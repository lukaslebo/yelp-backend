# Yelp frontend

## History
So we have recently discovered that Yelp wants to move his current stack to a new one based on Java+Spring and ES6+React, they also want to replace his old fashioned Bootstrap UI framework with a supercool UI. 

For this herculean task they will hire a whole new team and selection process is starting now. A prototype following their wireframes is mandatory for all those that want to participate.

### Teams
Here in Propulsion we have presented these three teams. We think that they are compensated in the best possible way. Our goal is that all teams reach almost the same features:

|         |            | 
| ------------- |:-------------:| 
| Fabio      | Kim | 
| Lukas      | Michal      | 
| Jeremy | Laz      |
| Jürg | Julya      |
| Gabriel | Patrick      |


### Backend stack
* Java8
* Spring Framework
* H2 but if you are into adventures try to implemented with PostgreSQL

## Features
This is a list of features that Yelp would like to see in all prototypes:
* A user should be able to create a new account.
* A user should be able to change its preferences: 
  * First name, last name, email and password.
* A user should be able to log in.
* A user should be able to log out.
* A user should be able to delete his/her account.
* A anonymous or a registered user can access to / where a list of restaurants is going to be presented:
  * An anonymous or a registered user in / can click on a restaurant and its information will be shown.
* An anonymous or a registered user can access to /about 
* An anonymous or a registered user can access to /contact
* An anonymous or a registered user can search for a restaurant by name, and a list will be shown with all the matches.
* A registered user can create a review.
* In a restaurant page a user can edit/delete his/her reviews.

## Domain
### User
* `id : Long`
* `firstName: String`
* `lastName: String`
* `email: String`
* `password: String`

### Restaurant
* `id : Long`
* `name: String`
* `address: String`
* `email: String`
* `phone: String`
* `logo: String`
* `url: String`

### Review
* `id : Long`
* `text: String`
* `rating: Integer`
* `dateCreated: LocalDate `
* `user: User`
* `restaurant: Restaurant`

## API endpoints

Public paths:
* `POST: /api/users/sign_in`: Body should have an email and a password.
* `POST: /api/users/sign_up`: Body should have an firstName, lastName, email and a password.
* `GET: /api/restaurants/search?=params`: Params has the name of the restaurant
* `GET: /api/restaurants/`
* `GET: /api/restaurants/:id`

Paths with auth:
* `PUT: /api/users/:id.`
* `DELETE: /api/user/:id`
* `PUT: /api/restaurant/:id/review/:id`
* `DELETE: /api/restaurant/:id/review/:id`
* `POST: /api/restaurant/:id/review`: Body should have text and rating property.


## Development process 
1. Go to the current milestone.
2. Assign an issue to yourself.
3. Solve it.
4. Create pull request.
5. Assign your partner to review it.


## Planning

We will use GitHub's [milestones](https://github.com/Propulsion-Academy/yelp-frontend/milestones) and [issues](https://github.com/Propulsion-Academy/yelp-frontend/issues) to track the progress of your project.

On Friday 17th at 14:00 every group will present their projects.

*** If coding from 9:00 to 18:00 (Monday - Friday)  is not enough time to finish, have in mind that the class is open 24/7 🤓 ***
