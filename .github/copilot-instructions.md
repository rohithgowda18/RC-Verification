# RC Shield - AI Coding Agent Instructions

## Project Overview
RC Shield is a **vehicle registration verification and fraud detection system** with a React frontend and Spring Boot backend. Users verify vehicle registration certificates (RC), detect fraud through vehicle searches, and RTO/police admins manage registrations with role-based access.

**Frontend Stack:** Vite + React 18 + TypeScript + shadcn/ui + TailwindCSS + React Router + React Query + Zod  
**Backend Stack:** Spring Boot 4.0 + Java 21 + MongoDB + JWT authentication

## Architecture & Data Flow

### Frontend (React)
- **Auth System** (`src/pages/Auth.tsx`): Email/password signup & signin via Spring Boot API with Zod validation
- **Dashboard** (`src/pages/Dashboard.tsx`): Role-based UI hub with tabs for verify, vehicles, analytics, user management
- **Verify RC** (`src/pages/Verify.tsx`): Search vehicles by RC number and perform fraud checks
- **Vehicles/Analytics/AdminUsers** (`src/pages/`): Role-gated pages for viewing data

**Authentication Flow:**
1. User signs up/in via `/api/auth/signup` or `/api/auth/signin`
2. Backend returns JWT token + user object with `role` field
3. Frontend stores in localStorage: `authToken` + `user` (JSON stringified)
4. Protected pages check token on mount; redirect to `/auth` if missing

### Backend (Spring Boot)
- **API Base URL**: `http://localhost:8081`
- **Entities** (`SmartVehicle/src/main/java/com/vehicle/SmartVehicle/entity/`):
  - `User.java`: User with email, password, role
  - `Vehicle.java`: RC records with status, insurance/PUC validity dates
  - `FraudFlag.java`: Fraud risk details
  - `Verification.java`: Audit trail of verifications

- **Controllers**:
  - `AuthController.java`: `/api/auth/signup`, `/api/auth/signin`, `/api/auth/logout`, `/api/auth/me`
  - `VehicleController.java`: `/api/vehicles/search`, `/api/vehicles/fraud-check`, `/api/vehicles`, `/api/vehicles/{id}`

**Database:** MongoDB with collections for users, vehicles, fraud_flags, verifications

## Development Workflows

### Frontend Setup & Running
```bash
npm install          # Install deps
npm run dev         # Dev server (port 5173)
npm run build       # Prod build (outputs dist/)
npm run lint        # ESLint check
```

### Backend Setup & Running
```bash
cd SmartVehicle
mvn clean install   # Build with Maven
mvn spring-boot:run # Runs on port 8081
```

**Key Backend Config** (`SmartVehicle/src/main/resources/application.properties`):
- `server.port=8081`
- `spring.data.mongodb.uri=<MongoDB Atlas connection>`
- `jwt.secret=<HS512 key, min 64 chars>`
- `jwt.expiration=604800000` (7 days in ms)

### Environment Setup
Frontend uses Vite defaults (no `.env` file needed for local development). Backend MongoDB URI and JWT secret are in `application.properties`.

### Path Aliases
`@/*` maps to `src/*` (tsconfig.json). Always use `@/` for imports in React code.

## Project Conventions & Patterns

### API Client Pattern
File: `src/lib/api.ts` centralizes all backend calls:
```tsx
const API_BASE_URL = "http://localhost:8081";

export const apiClient = {
  auth: {
    signUp: async (email, password, fullName) => { /* POST /api/auth/signup */ },
    signIn: async (email, password) => { /* POST /api/auth/signin */ },
  },
  vehicles: {
    search: async (rcNumber) => { /* GET /api/vehicles/search?rcNumber=... */ },
    performFraudChecks: async (vehicleId) => { /* POST /api/vehicles/fraud-check */ },
  },
};
```
Import: `import { apiClient } from "@/lib/api"`

### Authentication in Protected Pages
Pattern (see `Dashboard.tsx`, `Verify.tsx`):
```tsx
useEffect(() => {
  const checkAuth = () => {
    const token = localStorage.getItem("authToken");
    const userData = localStorage.getItem("user");
    if (!token || !userData) {
      navigate("/auth");
      return;
    }
    const user = JSON.parse(userData);
    setUserRole(user.role || "public");
  };
  checkAuth();
}, [navigate]);
```

**Key Roles:** `'public'` | `'buyer'` | `'police'` | `'rto_admin'`
- Frontend conditionally renders tabs: police/RTO see vehicles, analytics, admin tabs
- Backend enforces permissions via role check in controllers

### Form Validation
Use **Zod** schemas (example from `Auth.tsx`):
```tsx
const authSchema = z.object({
  email: z.string().email("Invalid email").max(255),
  password: z.string().min(6, "Min 6 chars").max(100),
});
// Catch ZodError separately from fetch errors
try {
  const validated = authSchema.parse(formData);
} catch (error: any) {
  if (error instanceof z.ZodError) {
    toast.error(error.errors[0].message);
  }
}
```

### Toast Notifications
Use **Sonner** from `import { toast } from "sonner"`:
```tsx
toast.success("Action completed!");
toast.error("Something went wrong");
```
All async operations (API calls) should dispatch toast in catch blocks.

### Styling System
- **Color tokens** in `src/index.css` as HSL custom properties: `--primary`, `--secondary`, `--success`, `--warning`, `--destructive`
- **Tailwind extends** via `tailwind.config.ts`: custom colors (`primary`, `success`, `sidebar`), gradients (`bg-gradient-hero`), shadows
- All component colors use HSL for dark mode compatibility
- Example: `<Button className="bg-primary text-primary-foreground">` (auto-applies HSL values)

### Conditional UI by Role
Dashboard example (role-gated tabs):
```tsx
{(userRole === "rto_admin" || userRole === "police") && (
  <TabsTrigger value="vehicles">
    <Database className="h-4 w-4 mr-2" />
    Vehicles
  </TabsTrigger>
)}
```

## Integration Points

### React Router Configuration
File: `src/App.tsx` defines all routes. **Add routes BEFORE the catch-all `"*"` route:**
```tsx
<Routes>
  <Route path="/" element={<Index />} />
  <Route path="/auth" element={<Auth />} />
  <Route path="/dashboard" element={<Dashboard />} />
  {/* ADD CUSTOM ROUTES HERE, BEFORE "*" */}
  <Route path="*" element={<NotFound />} />
</Routes>
```

### Component Libraries
- **shadcn/ui** (`src/components/ui/`): Auto-generated Radix UI primitives - do not edit directly
- **Lucide React**: Icons (imported from `lucide-react`)
- **React Hook Form** + **Zod**: Form handling + validation
- **React Query** (`@tanstack/react-query`): Already in App via `QueryClientProvider`, available for async state

## Critical Files & Roles
| File | Purpose |
|------|---------|
| `src/App.tsx` | Route definitions, QueryClient provider |
| `src/lib/api.ts` | Centralized API client for Spring Boot backend |
| `src/pages/Auth.tsx` | Signup/signin with JWT token storage |
| `src/pages/Dashboard.tsx` | Template for role-based UI rendering & auth checks |
| `src/pages/Verify.tsx` | Vehicle search & fraud check workflow |
| `tailwind.config.ts` | Design tokens, custom theme, gradients |
| `src/index.css` | HSL color variables |
| `SmartVehicle/pom.xml` | Java dependencies (Spring Boot, MongoDB, JWT) |
| `SmartVehicle/src/main/java/com/vehicle/SmartVehicle/controller/` | API endpoints |

## Debugging Tips
- **Auth fails**: Check localStorage has both `authToken` and `user` keys; verify Spring Boot is running on port 8081
- **API 404/500**: Check `SmartVehicle/src/main/java/com/vehicle/SmartVehicle/controller/` for endpoint definitions
- **Styles not applying**: Verify Tailwind content paths in `tailwind.config.ts` include your file
- **MongoDB connection error**: Check `application.properties` MongoDB URI is correct and cluster is accessible
- **CORS issues**: May need CORS filter in Spring Boot if frontend is on different origin
- **Port conflicts**: Frontend defaults to 5173; backend to 8081. Use `--port` flag if needed
