# Import libraries
import RPi.GPIO as GPIO
import pyrebase
import time
import datetime

config = {     
  "apiKey": "AIzaSyAwulOKVZQ0qaL6ejCcznbY3B-R1Xl-kG4",
  "authDomain": "test-3fe6c.firebaseapp.com",
  "databaseURL": "https://test-3fe6c-default-rtdb.europe-west1.firebasedatabase.app/",
  "storageBucket": "test-3fe6c.appspot.com"
}

firebase = pyrebase.initialize_app(config)

# Set GPIO numbering mode
GPIO.setmode(GPIO.BOARD)

# Set pins 11 & 12 as outputs, and define as PWM servo1 & servo2
GPIO.setup(11,GPIO.OUT)
servo1 = GPIO.PWM(11,50) # pin 11 for servo1

# Start PWM running on both servos, value of 0 (pulse off)
servo1.start(0)

print ("Load the pod bays. Nuts will release as programmed.")

try:
    while True:
        now = datetime.datetime.now() # get current date and time
        print (now)
        costam = round(time.time() * 1000)
        print (costam)
        
        database = firebase.database()
        
        testBucket = database.child("test")
        isFeedingStatus = testBucket.child("isFeeding").get().val()
        
        scheduleBucket = database.child("ScheduleInfo").get()
        
        turnAmount = 1
        
        for scheduleData in scheduleBucket.each():
            schedule = scheduleData.val()
            scheduleKey = scheduleData.key()
            amount = (schedule['amount'])
            feedingHour = (schedule['feedingHour'])
            feedingMinute = (schedule['feedingMinute'])
            if now.hour==feedingHour and now.minute==feedingMinute:
                database.child("test").update({"isFeeding": "1"})
                turnAmount = amount
                database.child("ScheduleInfo").child(scheduleKey).remove()
        
        #for scheduleid in scheduleBucket.shallow().get().each():
        #    productdb = scheduleBucket.child(scheduleid)
        #    print([id for id in productdb.shallow().get()])
        
        if (isFeedingStatus=="1"):
            print ("FEEDING...")
            for x in range(turnAmount):
                servo1.ChangeDutyCycle(12)
                time.sleep(0.3)
                servo1.ChangeDutyCycle(0)
                time.sleep(0.3)
                servo1.ChangeDutyCycle(2)
                time.sleep(0.3)
                servo1.ChangeDutyCycle(0)
                time.sleep(0.3)
            
            database.child("test").update({"isFeeding": "0"})
            
            timeStamp = round(time.time() * 1000)
            
            statsBucket = database.child("statsTable")
            
            #firebase = firebase.FirebaseApplication("https://test-3fe6c-default-rtdb.firebaseio.com", None)
            
            statsData = {"amount": turnAmount,
                         "dateTime": timeStamp
                         }
            
            statsBucket.push(statsData)
            
            #result = firebase.post("test-3fe6c-default-rtdb/statsTable", statsData)
            
            turnAmount = 1
           # break
        
        time.sleep(10) # pause to lower CPU activity

except KeyboardInterrupt: # If CTRL+C is pressed, exit cleanly:
    servo1.stop() # stop red PWM
    GPIO.cleanup() # cleanup all GPIO
    print("Goodbye!")
    
