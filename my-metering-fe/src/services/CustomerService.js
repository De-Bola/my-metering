import axios from "axios";

class CustomerService {
  static BASE_URL = "http://localhost:8080";

  static async login(username, password) {
    try {
      const response = await axios.post(`${this.BASE_URL}/api/auth/login`, {
        username,
        password,
      });
      return response.data;
    } catch (error) {
      console.error("Error logging in:", error);
      throw error;
    }
  }

  static async getLoggedInCustomer(token) {
    try {
      const response = await axios.get(`${this.BASE_URL}/api/customers/me`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      return response.data;
    }
    catch (error) {
      console.error("Error fetching logged-in customer:", error);
      throw error;
    } 
  }

  static async register(userdata) {
    try {
      const response = await axios.post(
        `${this.BASE_URL}/api/auth/register`,
        userdata
      );
      return response.data;
    } catch (error) {
      console.error("Error registering user:", error);
      throw error;
    }
  }
}

export default CustomerService;
