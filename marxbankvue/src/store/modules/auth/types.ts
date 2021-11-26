import { Status } from "@/store/types";

export interface AuthState {
  status: Status;
  tokenData: TokenData;
  autoLogout: boolean;
  statusCode: number;
}

export interface TokenData {
  userId: number | null;
  token: string | null;
  expiresIn: number | null;
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
