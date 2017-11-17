# Yelp backend

## RESTful Service / API

### Backend stack
* Java8
* Spring Framework
* H2
* Jwt

## Features
* A user can create a new account (sign up).
* A user is able to change its preferences:
  * First name, last name, email and password.
* A user can log in (and receive a web token).
* A user is able to delete his/her account.
* An anonymous or a registered user can access a list of restaurants:
  * An anonymous or a registered user can view a restaurant and its information / reviews.
* An anonymous or a registered user can search for a restaurant by name, and a list will be shown with all the matches.
* A registered user can create a review.
* A user can edit/delete his/her reviews.
* An admin can get a list of all users
* An admin can create / modify / delete a restaurant
* An admin can delete / modify any others users reviews / account.

## Domain
### User
* `id : Long`
* `firstName: String`
* `lastName: String`
* `email: String`
* `password: String`
* `reviews: Review`

### Restaurant
* `id : Long`
* `name: String`
* `address: String`
* `email: String`
* `phone: String`
* `logo: String`
* `url: String`
* `reviews: Review`

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
* `GET: /api/restaurants/search?q=params`: Params has the name of the restaurant
* `GET: /api/restaurants/`
* `GET: /api/restaurants/restaurant_id`

Paths with auth:
* `GET: /api/users/me`
* `PUT: /api/users/user_id.`: Body can have an firstName, lastName, email and/or password.
* `DELETE: /api/user/user_id`
* `POST: /api/restaurant/restaurant_id/review`: Body should have text and rating property.
* `PUT: /api/restaurant/restaurant_id/review/review_id`: Body can have a text and/or rating property.
* `DELETE: /api/restaurant/restaurant_id/review/review_id`


## Todo

* Write tests... /(^_^)\
