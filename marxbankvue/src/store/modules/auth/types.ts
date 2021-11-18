import { Status } from "@/store/types";

export interface AuthState {
  status: Status;
  token: string | null;
  userId: number | null;
  statusCode: number;
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
