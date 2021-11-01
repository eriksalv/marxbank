/**
 * Account interface
 */
export interface Account {
  readonly id: number;
  readonly userId: number;
  name: string;
  readonly accNumber: number;
  balance: number;
  interest: number;
  type: string;
}

export interface AccountState {
  accounts: Array<Account>;
}
