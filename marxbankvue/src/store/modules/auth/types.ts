export interface AuthState {
  status: string;
  token: string | null;
  userId: number | null;
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface SignUpRequest {
  username: string;
  email: string;
  password: string;
}

export interface LoginResponse {
  userId: number;
  token: string;
}
