import { Status } from "@/store/types";
import { Account } from "@/types/types";

/**
 * Account interface
 */

export interface AccountState {
  accountStatus: Status;
  accounts: Array<Account>;
}
