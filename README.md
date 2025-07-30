# React + NodeJS UI and API Automation Project

## Overview

This project contains:

- **React frontend** source code for a Todo application  
- **NodeJS backend API** source code  
- **UI Automation tests** for the React app using Playwright  
- **API Automation tests** for the NodeJS backend using REST-assured  

The goal is to provide a full-stack sample application with end-to-end functional UI and API testing automation.

---

## Features Tested

### UI Automation (Playwright)

- Login with valid and invalid credentials  
- Create, edit, delete todo items  
- Verify UI updates after actions  

### API Automation (REST-assured)

- POST /login authentication  
- CRUD operations on /items endpoint  
- Positive and negative test scenarios  

---

## Tools & Frameworks

| Tool               | Purpose                         |
|--------------------|---------------------------------|
| React              | Frontend framework              |
| NodeJS + Express   | Backend API                     |
| Playwright         | UI automation framework         |
| REST-assured       | API test automation framework   |
| Maven              | Java build and dependency tool  |
| JUnit & Cucumber   | Test runner and BDD framework   |

---

## Prerequisites

- Java 11+ installed  
- Maven installed  
- NodeJS and npm installed  
- Internet connection to download dependencies  

---

## Setup Instructions

1. Clone the repository:
   git clone git@github.com:BelestiY/UIAPIAutomationForReactNodeJsPlusApp.git

2. Install backend dependencies and start backend server:
cd backend
npm install
npm start

3. Install frontend dependencies and start frontend app:
cd frontend
npm install
npm start

4. Run tests from the root project directory:
Run UI tests:
mvn clean test -Dcucumber.filter.tags="@ui" -Dtest=runner.UITestRunner

Run API tests:
mvn clean test -Dcucumber.filter.tags="@api" -Dtest=runner.ApiTestRunner

Run all tests:
mvn clean test

## Assumptions and Limitations
- The backend server must be running before executing UI or API tests.
- Tests assume default URLs and ports for frontend and backend (can be customized in config).
- Tests currently cover basic functional flows; no performance or security tests included.
- API tests use REST-assured Java framework; UI tests use Playwright with Java bindings.

## Contribution
Feel free to raise issues or submit pull requests to improve the tests or add new features.

## Contact
For questions or feedback, please contact belestibdu@gmail.com.
