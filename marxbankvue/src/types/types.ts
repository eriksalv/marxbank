export interface LoginRequest {
  username: string;
  password: string;
}

export interface SignUpRequest {
  username: string;
  password: string;
  email: string;
}

export interface LoginResponse {
  userId: number;
  token: string;
}

export interface Userstate {
  userId: number;
  token: String;
}

export interface TransactionRequest {
  from: number;
  to: number;
  amount: number;
}
