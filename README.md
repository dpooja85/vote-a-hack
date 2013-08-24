Vote-A-Hack
=========

Rest API with a mobile web page for voting and larger presentation web pages for score display,timers, and leaderboards. Everything 
you need to run a hackathon at your office.

Setup Instructions:
1. install sbt : http://www.scala-sbt.org/release/docs/Getting-Started/Setup.html (i had best luck with manual install on osx)
2. install mysql
3. create database named "voting", app has this database name hardcoded and accessed by root with default empty password for now.
4. run src/main/db/create.sql in the voting database. You can run the other create scripts instead if you want to populate past jumptap hackathon data to see how results are displayed.
5. from within the git repo directory run 'sbt', as installed in step 1. This will bring you into an sbt console.
6. exectue 'container:start' to start a jetty instance on port 8080 running your app in the root context.

Urls:
1. To run the presentation app: http://localhost:8080/results.html  (has to run within jumptap right now due to hard coded intranet link)
