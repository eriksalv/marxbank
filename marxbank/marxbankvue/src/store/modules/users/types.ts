import { Status } from "@/store/types";

export interface User {
  readonly id: number;
  username: string;
  email: string;
}

export interface UserState {
  userStatus: Status;
  loggedInUser: User;
}
