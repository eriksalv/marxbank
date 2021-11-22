export interface User {
  readonly id: number;
  token: string;
}

export interface UserState {
  users: Array<User>;
}
