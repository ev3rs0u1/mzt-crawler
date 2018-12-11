@echo off
java -jar target\uberjar\mzt-crawler-0.1.0-standalone.jar > jav_report.html
move -y jav_report.html C:\Users\ev3rs0u1\Desktop\WORKSPACES\ev3rs0u1\public
C:\Users\ev3rs0u1\Desktop\WORKSPACES\ev3rs0u1\post.cmd
