// API client for Spring Boot backend
const API_BASE_URL = "http://localhost:8081";

async function handleResponse(response: Response) {
  const text = await response.text();
  
  if (!response.ok) {
    const errorMsg = text || `HTTP Error: ${response.status}`;
    throw new Error(errorMsg);
  }
  
  try {
    return text ? JSON.parse(text) : null;
  } catch {
    throw new Error(`Invalid JSON response: ${text}`);
  }
}

export const apiClient = {
  // Auth endpoints
  auth: {
    signUp: async (email: string, password: string, fullName: string) => {
      const response = await fetch(`${API_BASE_URL}/api/auth/signup`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password, fullName }),
      });
      return handleResponse(response);
    },

    signIn: async (email: string, password: string) => {
      const response = await fetch(`${API_BASE_URL}/api/auth/signin`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password }),
      });
      return handleResponse(response);
    },

    logout: async () => {
      const response = await fetch(`${API_BASE_URL}/api/auth/logout`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
      });
      return handleResponse(response);
    },

    getCurrentUser: async (token: string) => {
      const response = await fetch(`${API_BASE_URL}/api/auth/me`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${token}`,
        },
      });
      return handleResponse(response);
    },
  },

  // Vehicle endpoints
  vehicles: {
    search: async (rcNumber: string) => {
      const response = await fetch(
        `${API_BASE_URL}/api/vehicles/search?rcNumber=${rcNumber}`,
        {
          method: "GET",
          headers: { "Content-Type": "application/json" },
        }
      );
      return handleResponse(response);
    },

    performFraudChecks: async (vehicleId: string) => {
      const response = await fetch(`${API_BASE_URL}/api/vehicles/fraud-check`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ vehicleId }),
      });
      return handleResponse(response);
    },

    getAll: async () => {
      const response = await fetch(`${API_BASE_URL}/api/vehicles`, {
        method: "GET",
        headers: { "Content-Type": "application/json" },
      });
      return handleResponse(response);
    },

    getByStatus: async (status: string) => {
      const response = await fetch(`${API_BASE_URL}/api/vehicles/status/${status}`, {
        method: "GET",
        headers: { "Content-Type": "application/json" },
      });
      return handleResponse(response);
    },

    getById: async (id: string) => {
      const response = await fetch(`${API_BASE_URL}/api/vehicles/${id}`, {
        method: "GET",
        headers: { "Content-Type": "application/json" },
      });
      return handleResponse(response);
    },
  },
};
