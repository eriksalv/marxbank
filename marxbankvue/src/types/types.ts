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

export interface AccountRequest {
  type: String;
  name: String;
}

export interface DepositWithdrawRequest {
  amount: number;
  accountId: number;
}

export interface Account {
  readonly id: number;
  readonly userId: number;
  name: string;
  readonly accNumber: number;
  balance: number;
  interest: number;
  type: string;
}
