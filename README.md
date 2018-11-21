TutorRow
----

[![Stories in Ready](https://badge.waffle.io/jhyman2/TutorRow.png?label=ready&title=Ready)](http://waffle.io/jhyman2/TutorRow)

[![Join the chat at https://gitter.im/jhyman2/TutorRow](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/jhyman2/TutorRow?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

### What is TutorRow?

TutorRow is for students on a college campus to get help from other students in their class.

### How does it work?

If a student wishes to be tutored, they will simply navigate to the class they need assistance in and request to be tutored.

If a student wishes to tutor, they can navigate to a class and indicate that they are able to tutor. If matches are present, then the students are given the ability to contact each other.

### Why is this better than a tutoring service?

When I needed help in my classes (which was all the time), all it would take is for someone who knows the course material to break it down for me really quickly. Tutoring services require ramp-up time and they charge way too much money.

### My personal inspiration for building this

When I was taking a philosophy course calle deductive logic, I stopped paying attention in class and doing the required readings. I just happened to be swamped that semester and wound up putting this class on the back burner. I had a fantastic friend in the class who told me he could spend an hour in the library with me reviewing the important parts of the readings with me that were going to be on the midterm. After 1 hour, he had told me everything the professor said was going to be on the exam and then taught me the concepts we learned in class. I literally got 100% on this exam and realized how great learning from peers can be.

----
## Building
- You will need auth.js. It's a secret. It goes in src/server/auth.js
- Install [yarn](https://yarnpkg.com) on your machine
- From the root of this directory, run `yarn install`
- Download [postgres](https://www.postgresql.org/download/) and install WITH pgadmin.
- Create a server in pgadmin called tutorrow
- Create a database in pgadmin called tutorrow
- Highlight the tutorrow Database, and go to Tools -> Restore
- Navigate to schema.sql and restore using that file
- From the root, run `yarn start-dev`
- Go to `localhost:8080` in web browser

![SSS1](https://github.com/jhyman2/TutorRow/blob/master/schema.png?raw=true)
