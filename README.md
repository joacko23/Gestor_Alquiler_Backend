<!-- README – Gestor_Alquiler Backend (Spring Boot) -->

<p align="center">
  <img src="https://capsule-render.vercel.app/api?type=waving&color=0:004e92,100:000428&height=150&section=header&text=Gestor%20Alquiler%20–%20Backend&fontSize=40&fontColor=ffffff" />
</p>

<p align="center">
  <b>Java + Spring Boot REST API</b><br>
  <i>Vehicle & appliance rental management system</i>
</p>

---

# 🚀 Overview

**Gestor_Alquiler_Backend** is a complete **REST API built with Spring Boot**, designed to manage rentals of:

- 🚗 **Vehicles** (cars, motos, trucks — rented per hour)  
- 📦 **Appliances** (electrodomésticos — rented per day)

It includes user authentication with **JWT**, availability validation, rental cost calculation, DTO mapping, and follows clean architecture principles with patterns such as **Factory Method** and **Strategy**.

This backend powers the Angular frontend of the same system.

---

# ✅ Features

### 🔐 **Authentication & Security**
- Login with email + password  
- **JWT tokens**  
- Stateless security (no sessions)  
- CORS configured for frontend routes  

### 🚗 **Rental Management**
- Create, edit, delete rentals  
- Hourly pricing (vehicles)  
- Daily pricing (appliances)  
- Automatic rental cost calculation  
- Prevents double-booking of the same item  

### 📦 **Inventory / Alquilables**
- Separate models for **vehicles** and **appliances**  
- Availability tracking  
- CRUD operations  
- DTO mapping for front-end integration  
- Factory pattern for object creation  
- Strategy pattern for pricing logic  

### 👤 **User Management**
- Login  
- Roles (student/teacher or custom)  
- Rental tied to authenticated user  

---

# 🧠 Architecture & Design

### ✅ **Clean Layered Structure**
- `controllers/`  
- `services/`  
- `repositories/`  
- `entities/`  
- `dtos/`  
- `strategies/` for pricing  
- `factory/` for Alquilable creation  

### ✅ **Patterns Implemented**
- **Factory Method** → Creates specific Alquilable subclasses  
- **Strategy** → Pricing calculation (hours/days)  
- **DTO + Mapper** → Clean API communication  
- **Singleton** (LoginDao in original version)

### ✅ **Spring Components**
- Spring Web  
- Spring Data JPA  
- Hibernate  
- Spring Security  
- MySQL Connector  
- ModelMapper  

---

---

# 🗄️ Database Diagram (Simplified)

Users
 ├── id
 ├── email
 ├── password
 └── role

Alquilables
 ├── id
 ├── type (vehicle / appliance)
 ├── brand
 ├── available
 └── extra fields per subclass

Rentals
 ├── id
 ├── user_id (FK)
 ├── alquilable_id (FK)
 ├── start_date
 ├── end_date
 └── total_cost 
📡 API Endpoints (Resumen)

🔐 Auth
| Method | Endpoint      | Description       |
| ------ | ------------- | ----------------- |
| POST   | `/auth/login` | Returns JWT token |

🚗 Alquilables
| Method | Endpoint            | Description    |
| ------ | ------------------- | -------------- |
| GET    | `/alquilables`      | List all items |
| POST   | `/alquilables`      | Create         |
| PUT    | `/alquilables/{id}` | Update         |
| DELETE | `/alquilables/{id}` | Delete         |

📄 Rentals
| Method | Endpoint        | Description                |
| ------ | --------------- | -------------------------- |
| POST   | `/rentals`      | Create rental if available |
| GET    | `/rentals/user` | Rentals by logged user     |
| DELETE | `/rentals/{id}` | Cancel rental              |

▶️ How to Run

✅ 1. Clone the repo
git clone https://github.com/joacko23/Gestor_Alquiler_Backend.git
cd Gestor_Alquiler_Backend

✅ 2. Configure database (MySQL)
Create database:
CREATE DATABASE gestor_alquiler;

Configure credentials in application.properties:
spring.datasource.url=jdbc:mysql://localhost:3306/gestor_alquiler
spring.datasource.username=root
spring.datasource.password=tu_password
spring.jpa.hibernate.ddl-auto=update

✅ 3. Run the app
mvn spring-boot:run

Backend available at:
➡️ http://localhost:8080

✅ Technologies

Java 17+

Spring Boot

Spring Security + JWT

MySQL

Hibernate / JPA

ModelMapper

👨‍💻 Author

Joaquín Domenech
Full-Stack Developer
📧 joackodomenech@gmail.com

<p align="center"> <img src="https://capsule-render.vercel.app/api?type=waving&color=0:000428,100:004e92&height=150&section=footer" /> </p> 


