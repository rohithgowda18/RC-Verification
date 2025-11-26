# RC Shield - Complete Project Overview

## 📋 Executive Summary

**RC Shield** is a comprehensive **Vehicle Registration Verification & Fraud Detection System** designed to help RTO (Regional Transport Office) administrators, police, and buyers verify vehicle registration certificates (RC), detect fraudulent registrations, and manage vehicle data efficiently.

The system uses a **modern full-stack architecture** combining a React 18 frontend with a Spring Boot 4.0 backend, secured with JWT authentication and powered by MongoDB Atlas.

---

## 🎯 Project Goals

1. **Prevent Vehicle Fraud** - Detect duplicate registrations, forged documents, and suspicious patterns
2. **Enable Transparent Verification** - Allow quick RC verification via QR codes or manual search
3. **Role-Based Access Control** - Different permissions for buyers, police, and RTO admins
4. **Audit & Compliance** - Maintain complete verification history for legal compliance
5. **Scalability** - Handle high volume of verifications via cloud infrastructure

---

## 🏗️ Architecture Overview

### System Components

```
┌─────────────────────────────────────────────────────────────┐
│                     Frontend (React 18)                      │
│  Port 5173 - Vite Dev Server                                │
│  - Authentication Pages                                      │
│  - Dashboard with Role-Based UI                             │
│  - Vehicle Verification Interface                           │
│  - Admin Analytics & Management                             │
└─────────────────────────────────────────────────────────────┘
                            ↓ REST API (HTTP)
                    [JWT Token Authentication]
                            ↓
┌─────────────────────────────────────────────────────────────┐
│                Backend (Spring Boot 4.0)                     │
│  Port 8081 - REST API Server                                │
│  - Authentication Service (JWT)                              │
│  - Vehicle Management Service                               │
│  - Fraud Detection Engine                                   │
│  - Verification Audit Logging                               │
└─────────────────────────────────────────────────────────────┘
                            ↓
┌─────────────────────────────────────────────────────────────┐
│         Database (MongoDB Atlas Cloud)                       │
│  - users (Authentication & Authorization)                   │
│  - vehicles (RC Records)                                    │
│  - fraud_flags (Fraud Detection Results)                    │
│  - verifications (Audit Trail)                              │
└─────────────────────────────────────────────────────────────┘
```

---

## 💻 Technology Stack

### Frontend Stack
```
├── Framework & Bundler
│   ├── Vite 5.4 - Fast module bundler
│   ├── React 18.3 - UI library
│   └── TypeScript 5.8 - Type safety
│
├── UI & Styling
│   ├── shadcn/ui - Radix UI component library
│   ├── Tailwind CSS 3.4 - Utility-first CSS
│   └── Lucide React - Icon library
│
├── State & API Management
│   ├── React Router 6.30 - Client-side routing
│   ├── React Query 5.83 - Server state management
│   └── Zod 3.25 - Schema validation
│
├── Forms & Validation
│   ├── React Hook Form 7.61 - Form handling
│   └── Zod - Runtime validation
│
└── Notifications
    └── Sonner 1.7 - Toast notifications
```

### Backend Stack
```
├── Framework
│   ├── Spring Boot 4.0 - Web application framework
│   ├── Spring Data MongoDB - Database abstraction
│   └── Spring Security - Authentication & Authorization
│
├── Language
│   └── Java 21 - Modern JDK with latest features
│
├── Database
│   ├── MongoDB Driver 5.6 - Document database client
│   └── MongoDB Atlas - Cloud database (production)
│
├── Authentication & Security
│   ├── JJWT 0.12.3 - JWT token management
│   ├── Lombok 1.18 - Boilerplate reduction
│   └── Jakarta Validation - Bean validation
│
├── Build & Development
│   ├── Maven 3.9+ - Dependency management
│   ├── Spring DevTools - Hot reload
│   └── Tomcat 11.0 - Embedded servlet container
│
└── Utilities
    └── dotenv-java 3.0 - .env file loading
```

---

## 📁 Project Structure

### Frontend Structure
```
rc-shield-main/
├── src/
│   ├── pages/
│   │   ├── Index.tsx          # Landing page
│   │   ├── Auth.tsx           # Login/Signup
│   │   ├── Dashboard.tsx      # Main hub (role-based)
│   │   ├── Verify.tsx         # Vehicle verification
│   │   ├── Vehicles.tsx       # Vehicle listing (admin)
│   │   ├── Analytics.tsx      # Statistics dashboard
│   │   ├── AdminUsers.tsx     # User management
│   │   └── NotFound.tsx       # 404 page
│   │
│   ├── components/
│   │   ├── NavLink.tsx        # Custom nav component
│   │   └── ui/                # shadcn/ui components
│   │       ├── button.tsx, card.tsx, dialog.tsx, etc.
│   │
│   ├── lib/
│   │   ├── api.ts             # Centralized API client
│   │   └── utils.ts           # Utility functions
│   │
│   ├── hooks/
│   │   ├── use-mobile.tsx     # Responsive detection
│   │   └── use-toast.ts       # Toast notifications
│   │
│   ├── App.tsx                # Route configuration
│   ├── main.tsx               # Entry point
│   └── index.css              # Global styles
│
├── vite.config.ts             # Vite configuration
├── tailwind.config.ts         # Tailwind theme
├── tsconfig.json              # TypeScript config
└── package.json               # Dependencies

```

### Backend Structure
```
SmartVehicle/
├── src/main/java/com/vehicle/SmartVehicle/
│   ├── entity/
│   │   ├── User.java          # User model with validation
│   │   ├── Vehicle.java       # Vehicle/RC model
│   │   ├── FraudFlag.java     # Fraud detection model
│   │   └── Verification.java  # Verification audit model
│   │
│   ├── repository/
│   │   ├── UserRepository.java
│   │   ├── VehicleRepository.java
│   │   ├── FraudFlagRepository.java (enhanced queries)
│   │   └── VerificationRepository.java (enhanced queries)
│   │
│   ├── service/
│   │   ├── AuthService.java   # Authentication logic
│   │   ├── VehicleService.java # Vehicle operations
│   │   └── DataLoader.java    # Sample data initialization
│   │
│   ├── controller/
│   │   ├── AuthController.java # Auth endpoints
│   │   └── VehicleController.java # Vehicle endpoints
│   │
│   ├── security/
│   │   ├── JwtTokenProvider.java # JWT generation/validation
│   │   └── SecurityConfig.java # Spring Security setup
│   │
│   ├── config/
│   │   └── EnvConfig.java     # .env file loading
│   │
│   └── SmartVehicleApplication.java # Main entry point
│
├── src/main/resources/
│   ├── application.properties  # Configuration (with env vars)
│   └── application.yml
│
├── pom.xml                    # Maven dependencies
├── .env                       # Environment variables
└── target/                    # Built JAR

```

---

## 🔐 Authentication & Authorization

### Authentication Flow

1. **User Registration:**
   ```
   User enters email/password/fullName → 
   Frontend validation (Zod) → 
   POST /api/auth/signup → 
   Backend hashes password → 
   Stores in MongoDB → 
   Returns JWT token & user object
   ```

2. **User Login:**
   ```
   User enters email/password → 
   Frontend validation → 
   POST /api/auth/signin → 
   Backend validates credentials → 
   Generates JWT token (7-day expiry) → 
   Returns token & user data
   ```

3. **Protected API Calls:**
   ```
   Frontend stores: localStorage["authToken"] + localStorage["user"] →
   Each API request includes: Authorization: Bearer {token} →
   Backend validates JWT signature & expiry →
   Extracts user info from token claims
   ```

### Role-Based Access Control

| Role | Permissions | UI Access |
|------|-------------|-----------|
| **public** | View own verifications | Verify tab only |
| **buyer** | Verify vehicles, view history | Verify tab |
| **police** | Full access + fraud reporting | All tabs except admin |
| **rto_admin** | Complete system control | All tabs + user management |

### JWT Token Structure
```
Header: { "alg": "HS512", "typ": "JWT" }
Payload: { 
  "sub": "user@email.com",
  "iat": 1700000000,
  "exp": 1700604800,
  "role": "police"
}
Signature: HMAC-SHA512(header.payload, JWT_SECRET)
```

---

## 📊 Database Schema

### Collections Overview

#### 1. **users** Collection
```javascript
{
  _id: ObjectId,
  email: String (unique),           // user@example.com
  password: String (bcrypt hashed), // $2a$10$...
  fullName: String,                 // John Doe
  role: String,                     // public|buyer|police|rto_admin
  createdAt: Date                   // 2025-11-26T10:00:00Z
}

Indexes:
- email (unique)
- (email, role) compound index
```

#### 2. **vehicles** Collection
```javascript
{
  _id: ObjectId,
  rcNumber: String (unique),        // AP01AB1234
  ownerName: String,                // Rajesh Kumar
  chassisNumber: String (unique),   // MSEPC123456789
  engineNumber: String (unique),    // 1K5FH23456789
  vehicleMake: String,              // Maruti
  vehicleModel: String,             // Swift
  vehicleYear: Number,              // 2022
  registrationDate: Date,           // 2022-05-15
  registeredState: String,          // Andhra Pradesh
  status: String,                   // active|inactive|suspended
  insuranceValidUntil: Date,        // 2025-12-31
  pucValidUntil: Date,              // 2025-12-31
  qrCode: String,                   // QR12345ABC
  createdAt: Date                   // 2025-11-26T10:00:00Z
}

Indexes:
- rcNumber (unique)
- chassisNumber (unique)
- engineNumber (unique)
- (status, createdAt) compound index
```

#### 3. **fraud_flags** Collection
```javascript
{
  _id: ObjectId,
  vehicleId: ObjectId,              // Reference to vehicle
  fraudScore: Number,               // 0.0 to 1.0
  flaggedBy: ObjectId,              // Reference to user
  flagType: String,                 // duplicate_chassis|expired_insurance
  description: String,              // Duplicate chassis number detected
  resolved: Boolean,                // false
  resolvedAt: Date,                 // null
  resolutionNotes: String,          // null
  createdAt: Date                   // 2025-11-26T10:00:00Z
}

Indexes:
- (vehicleId, resolved) compound index
- (createdAt, fraudScore) for analytics
```

#### 4. **verifications** Collection
```javascript
{
  _id: ObjectId,
  vehicleId: ObjectId,              // Reference to vehicle
  verifiedBy: ObjectId,             // Reference to user
  verificationType: String,         // qr_scan|manual_search|batch_check
  result: String,                   // verified|suspicious|blocked
  fraudScore: Number,               // 0.0 to 1.0
  verificationIP: String,           // 192.168.1.100
  verificationLocation: String,     // Mumbai
  createdAt: Date                   // 2025-11-26T10:00:00Z
}

Indexes:
- (vehicleId, result) compound index
- (verifiedBy, createdAt) for user history
- (fraudScore) for analytics
```

---

## 🔌 API Endpoints

### Authentication Endpoints

| Method | Endpoint | Purpose | Auth |
|--------|----------|---------|------|
| POST | `/api/auth/signup` | Register new user | None |
| POST | `/api/auth/signin` | Login user | None |
| POST | `/api/auth/logout` | Logout user | JWT |
| GET | `/api/auth/me` | Get current user | JWT |

**Example Requests:**
```bash
# Sign Up
curl -X POST http://localhost:8081/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","password":"password123","fullName":"John Doe"}'

# Sign In
curl -X POST http://localhost:8081/api/auth/signin \
  -H "Content-Type: application/json" \
  -d '{"email":"user@example.com","password":"password123"}'

# Response
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "user": {
    "id": "507f1f77bcf86cd799439011",
    "email": "user@example.com",
    "fullName": "John Doe",
    "role": "public"
  }
}
```

### Vehicle Endpoints

| Method | Endpoint | Purpose | Auth |
|--------|----------|---------|------|
| GET | `/api/vehicles/search?rcNumber=AP01AB1234` | Search vehicle by RC | JWT |
| POST | `/api/vehicles/fraud-check` | Check fraud for vehicle | JWT |
| GET | `/api/vehicles` | List all vehicles | JWT |
| GET | `/api/vehicles/{id}` | Get vehicle details | JWT |
| GET | `/api/vehicles/status/{status}` | Filter by status | JWT |

**Example Requests:**
```bash
# Search Vehicle
curl -X GET "http://localhost:8081/api/vehicles/search?rcNumber=AP01AB1234" \
  -H "Authorization: Bearer {JWT_TOKEN}"

# Perform Fraud Check
curl -X POST http://localhost:8081/api/vehicles/fraud-check \
  -H "Authorization: Bearer {JWT_TOKEN}" \
  -H "Content-Type: application/json" \
  -d '{"vehicleId":"507f1f77bcf86cd799439011"}'
```

---

## 🚀 Development & Deployment

### Local Development Setup

**Prerequisites:**
- Node.js 16+ (for frontend)
- Java 21 (for backend)
- MongoDB Atlas account (cloud database)
- Git

**Frontend Setup:**
```bash
# Install dependencies
npm install

# Start dev server (port 5173)
npm run dev

# Build for production
npm run build

# Run linter
npm run lint
```

**Backend Setup:**
```bash
cd SmartVehicle

# Install dependencies
mvn clean install

# Run locally (port 8081)
java -jar target/SmartVehicle-0.0.1-SNAPSHOT.jar

# Or with Maven
mvn spring-boot:run
```

**Configuration:**
- Frontend: No `.env` file needed (uses Vite defaults)
- Backend: `.env` file with:
  ```
  MONGODB_URI=mongodb+srv://username:password@cluster.mongodb.net/rc_shield
  JWT_SECRET=your-256-bit-secret-key-min-64-chars
  ```

### Data Seeding

The `DataLoader` class automatically initializes sample data on first startup:
- 3 test users (buyer, police, rto_admin)
- 2 test vehicles with complete details
- Sample fraud flags & verifications

Only runs if database is empty (production-safe).

---

## 📈 Key Features

### 1. **Vehicle Verification**
- QR code scanning
- Manual RC number search
- Real-time fraud scoring
- Insurance & PUC validity checking

### 2. **Fraud Detection Engine**
- Duplicate chassis/engine detection
- Insurance expiry alerts
- PUC validity monitoring
- Suspicious pattern recognition
- Manual admin review workflow

### 3. **Role-Based Dashboard**
- **Public/Buyer**: Verify vehicles, view history
- **Police**: Fraud reporting, investigation tools
- **RTO Admin**: Full system management, user administration

### 4. **Audit Trail**
- Complete verification history
- IP tracking
- Location logging
- Timestamp recording
- User accountability

### 5. **Analytics Dashboard**
- Verification statistics
- Fraud detection rates
- Verification trends
- User activity reports

---

## ⚙️ Configuration & Environment

### Backend Configuration
```properties
# Server
server.port=8081
spring.application.name=rc-shield-api

# MongoDB
spring.data.mongodb.uri=${MONGODB_URI}
spring.data.mongodb.auto-index-creation=true

# JWT
jwt.secret=${JWT_SECRET}
jwt.expiration=604800000  # 7 days in milliseconds

# Logging
logging.level.root=INFO
logging.level.com.vehicle=DEBUG
```

### Frontend Configuration
```typescript
// src/lib/api.ts
const API_BASE_URL = "http://localhost:8081";

// Automatically configured via vite.config.ts path aliases
// @/* maps to src/*
```

---

## 🔍 Debugging & Troubleshooting

### Common Issues

| Issue | Solution |
|-------|----------|
| **MongoDB Connection Failed** | Check `.env` MONGODB_URI, verify IP whitelist in Atlas |
| **JWT Token Expired** | Clear localStorage, re-login with fresh token |
| **API 404 Errors** | Verify backend is running on port 8081 |
| **CORS Issues** | Add CORS filter in Spring Security config |
| **Frontend Can't Find API** | Check API_BASE_URL in `src/lib/api.ts` |
| **Port Already in Use** | Change port in application.properties or use `--port` flag |

---

## 📝 Development Conventions

### Code Style
- **Frontend**: React hooks, functional components, TypeScript strict mode
- **Backend**: Spring Boot best practices, dependency injection, repository pattern
- **API**: RESTful conventions, proper HTTP status codes
- **Database**: Normalized schema with proper indexes

### Error Handling
- Frontend: Zod validation + try-catch + Sonner toast
- Backend: Proper exception handling + meaningful HTTP responses
- Database: Index constraints + validation annotations

### Testing
- Use `mvn test` for backend unit tests
- Set up Jest for frontend component testing

---

## 📚 Documentation Files

- **`.github/copilot-instructions.md`** - AI agent guidelines
- **`DATABASE_SETUP.md`** - Database schema & indexes
- **`README.md`** - Project overview
- **`package.json`** - Frontend dependencies
- **`SmartVehicle/pom.xml`** - Backend dependencies

---

## 🎓 Learning Resources

### Frontend
- [React Documentation](https://react.dev)
- [Tailwind CSS Docs](https://tailwindcss.com)
- [shadcn/ui Components](https://ui.shadcn.com)
- [React Router Guide](https://reactrouter.com)

### Backend
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Spring Data MongoDB](https://spring.io/projects/spring-data-mongodb)
- [JWT Best Practices](https://tools.ietf.org/html/rfc7519)
- [MongoDB Atlas Docs](https://docs.atlas.mongodb.com)

---

## ✅ Project Status

- ✅ Frontend: Complete (Auth, Dashboard, Verify, Admin panels)
- ✅ Backend: Running (API endpoints, JWT auth, MongoDB integration)
- ✅ Database: Connected to MongoDB Atlas
- ✅ Authentication: JWT-based with role-based access control
- ✅ Sample Data: Auto-loaded on first startup
- ✅ Documentation: Complete with setup guides

---

## 🤝 Contributing Guidelines

1. **Feature Development**: Create feature branch from `main`
2. **Commits**: Use descriptive messages (`fix:`, `feat:`, `docs:` prefixes)
3. **Code Review**: Ensure tests pass and linter is clean
4. **Deployment**: Merge to `main` after approval

---

## 📞 Support

For issues, questions, or improvements:
1. Check the documentation files
2. Review the copilot-instructions.md for coding patterns
3. Check MongoDB Atlas dashboard for data issues
4. Review application logs for error messages

---

**Last Updated**: November 26, 2025  
**Version**: 1.0.0-SNAPSHOT  
**Status**: Production Ready ✨
