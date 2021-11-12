import { RootState } from "@/store/types";
import { Account } from "@/types/types";
import { GetterTree } from "vuex";
import { AccountState } from "./types";

export const getters: GetterTree<AccountState, RootState> = {
  allAccounts: (state): Array<Account> => state.accounts,
  /**
   * Filters accounts by name. The filter passes as long as
   * the account name icludes the filter string, such that it does not
   * have to match exactly.
   * @param state accounts to perform filter on
   * @param filter String value to filter accounts on
   * @returns filtered array of accounts
   */
  filterAccountsByName:
    (state) =>
    (filter: String): Array<Account> => {
      if (!filter) {
        return [];
      }
      return state.accounts.filter((account) => {
        return account.name
          .toLowerCase()
          .includes(filter.toString().toLowerCase());
      });
    },
  filterAccountsByUserId:
    (state) =>
    (userId: number): Array<Account> => {
      return state.accounts.filter((account) => account.userId === userId);
    },
  /**
   * Går det an å bruke andre gettere inne i en getter?
   * @param state
   * @returns
   */
  filterAccountsByUserIdAndName:
    (state) =>
    (userId: number, filter: String): Array<Account> => {
      if (!filter) {
        return [];
      }
      return state.accounts
        .filter((account) => account.userId === userId)
        .filter((account) => {
          return account.name
            .toLowerCase()
            .includes(filter.toString().toLowerCase());
        });
    },
  getAccountById:
    (state) =>
    (id: number): Account | undefined => {
      return state.accounts.find((acc) => acc.id === id);
    },
};
