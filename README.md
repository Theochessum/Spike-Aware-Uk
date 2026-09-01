{\rtf1\ansi\ansicpg1252\cocoartf2822
\cocoatextscaling0\cocoaplatform0{\fonttbl\f0\fswiss\fcharset0 Helvetica;\f1\fnil\fcharset0 HelveticaNeue;}
{\colortbl;\red255\green255\blue255;}
{\*\expandedcolortbl;;}
\paperw11900\paperh16840\margl1440\margr1440\vieww12740\viewh13320\viewkind0
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\f0\fs24 \cf0 Spike Aware UK - Resource management system\
\

\
##DESCRIPTION\
Console-based resource management system built for Spike Aware UK.\
Allows public users to browse and search related spiking material categorised by research and awareness material.\
Admins can manage resources and accounts.\
Built with Gradle, Java and SQLite\
\
\
##HOW TO RUN\
1. Open the project in IntelliJ IDEA\
2. Ensure Java 17 or above is installed \
3. run Main.java\
4. SQLite database is created automatically on startup of the program (spikeaware.db)\
\
\
##SYSTEM REQUIREMENTS \
- Java 17 or above\
- IntelliJ IDEA\
\
\
##HARDCODED LOGIN CREDENTIALS \
\
Admin:            Admin1 / Password123\
Superadmin:  Superadmin1 / Super123\
Note: passwords for demonstrational purposes only \
\
\
##FEATURES\
Public user:\
- Browse approved awareness and research resources by category \
- Search resources by title \
- view full resource contents in console \
- emergency help information \
\
\
##Admin:\
- Add and update resources (Submitted as pending)\
- view all analytics \
- view all resources\
\
\
##Super Admin (Extends admin):\
- All admin features\
- Delete resources \
- Approve pending resources \
- Manage admin accounts (Create/Remove/List)\
- Export resources to CSV file (resources.csv)\
- Export analytics to CSV file (analytics.csv)\
\
\
Deviations from the report 010-1 plan\
- The ModerationQueue class was planned in the UML diagram, but was removed as SQLite handles \
all moderation functionality.\
- Keyworld was removed from the public search menu because it was poorly suited for a terminal \
application, still available through the admin menus \
\
\
##REFERENCES \
\
\pard\pardeftab560\slleading20\partightenfactor0

\f1\fs26 \cf0 Bro Code (2025) *Java Full Course for Free*, YouTube. Available at: https://www.youtube.com/watch?v=xTtL8E4LzTQ (Accessed: 21 February 2026).\
\
Gradle Inc. (no date) *Gradle User Manual*, Gradle. Available at: https://docs.gradle.org/current/userguide/userguide.html (Accessed: 21 February 2026).\
\
Oracle (2024) *Java 21 Documentation*, Oracle. Available at: https://docs.oracle.com/en/java/javase/21/ (Accessed: 21 February\
 2026).\
\
SQLiteTutorial.net (no date) *SQLite JDBC Driver*, SQLite Tutorial. Available at: https://www.sqlitetutorial.net/sqlite-java/sqlite-jdbc-driver/ (Accessed: 10 March 2026).\
\
Telusko (2024) *Java Database Connectivity | JDBC*, YouTube. Available at: https://www.youtube.com/watch?v=7v2OnUti2eM (Accessed: 1 March 2026).\
\
user14187680 (2021) *Create a table in SQLite with the following fields*, Stack Overflow. Available at: https://stackoverflow.com/questions/66579936/create-a-table-in-sqlite-with-the-following-fields (Accessed: 15 March 2026).\
\
user7627726 (2024) *How to print color in console using System.out.println*, Stack Overflow. Available at: https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println (Accessed: 2 April 2026).}
