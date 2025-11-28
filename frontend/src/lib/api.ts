// API client for Spring Boot backend
const API_BASE_URL = "http://localhost:8080";

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
  // Placeholder auth methods (backend auth not present in this module)
  auth: {
    signUp: async (_email: string, _password: string, _fullName: string) => {
      throw new Error("Auth API not implemented in backend");
    },
    signIn: async (_email: string, _password: string) => {
      throw new Error("Auth API not implemented in backend");
    },
  },
  rc: {
    // Public endpoints
    search: async (rcNumber: string) => {
      const response = await fetch(
        `${API_BASE_URL}/api/rc/search?rcNumber=${encodeURIComponent(rcNumber)}`,
        {
          method: "GET",
          headers: { "Content-Type": "application/json" },
        }
      );
      return handleResponse(response);
    },

    getAll: async () => {
      const response = await fetch(`${API_BASE_URL}/api/rc`, {
        method: "GET",
        headers: { "Content-Type": "application/json" },
      });
      return handleResponse(response);
    },

    getById: async (id: string) => {
      const response = await fetch(`${API_BASE_URL}/api/rc/${id}`,
        {
          method: "GET",
          headers: { "Content-Type": "application/json" },
        }
      );
      return handleResponse(response);
    },

    // Admin-only endpoints (require X-ADMIN-KEY)
    create: async (rc: any, adminKey: string) => {
      const response = await fetch(`${API_BASE_URL}/api/rc`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          "X-ADMIN-KEY": adminKey,
        },
        body: JSON.stringify(rc),
      });
      return handleResponse(response);
    },

    update: async (id: string, rc: any, adminKey: string) => {
      const response = await fetch(`${API_BASE_URL}/api/rc/${id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          "X-ADMIN-KEY": adminKey,
        },
        body: JSON.stringify(rc),
      });
      return handleResponse(response);
    },

    remove: async (id: string, adminKey: string) => {
      const response = await fetch(`${API_BASE_URL}/api/rc/${id}`, {
        method: "DELETE",
        headers: {
          "X-ADMIN-KEY": adminKey,
        },
      });
      return handleResponse(response);
    },
  },
};
