import { User, UserState } from "@/store/modules/users/types";
import { RootState } from "@/store/types";
import { getters } from "../store/modules/users/getters";
import { mutations } from "../store/modules/users/mutations";
import axios from "axios";
import AxiosMockAdapter from "axios-mock-adapter";
import { actions } from "../store/modules/users/actions";
import { EditUserRequest } from "../types/types";

const rootState: RootState = {
  message: "",
};

const testState: UserState = {
  userStatus: "",
  loggedInUser: {
    id: 1,
    username: "test",
    email: "test@test.com",
  },
};

describe("getters", () => {
  it("test get loggedInUser", () => {
    const actual = getters.getLoggedInUser(testState, null, rootState, null);

    expect(actual).toEqual(testState.loggedInUser);
  });
  it("test get user status", () => {
    const actual = getters.getUserStatus(testState, null, rootState, null);

    expect(actual).toEqual(testState.userStatus);
  });
});

describe("mutations", () => {
  it("test loggedInUser", () => {
    const newUser = {
      id: 2,
      username: "username",
      email: "email@email.com",
    };

    mutations.setLoggedInUser(testState, newUser);

    expect(testState.loggedInUser).toEqual(newUser);
  });
  it("test set user status without error", () => {
    const newStatus = "success";

    mutations.setUserStatus(testState, { status: newStatus });

    expect(testState.userStatus).toEqual(newStatus);
  });
  it("test set user status with error", () => {
    const newStatus = "error";

    try {
      mutations.setUserStatus(testState, {
        status: newStatus,
        errorMsg: "something went wrong",
      });
    } catch (e: any) {
      expect(e.message).toBe("something went wrong");
    }

    expect(testState.userStatus).toEqual(newStatus);
  });
});

describe("actions", () => {
  let mock: AxiosMockAdapter;

  beforeAll(() => {
    mock = new AxiosMockAdapter(axios);
  });

  afterEach(() => {
    mock.reset();
  });

  describe("fetch by id", () => {
    it("fetch user", async () => {
      const commit = jest.fn();
      const request: number = 1;
      const rootGetters = {
        getToken: "token",
      };
      const response: any = {
        id: 1,
        username: "test",
        email: "test@test.com",
      };
      const expected: User = {
        id: 1,
        username: "test",
        email: "test@test.com",
      };
      const fetchUserById = actions.fetchUserById as Function;
      mock.onGet(`/users/${request}`).reply(200, response);

      await fetchUserById({ commit, rootGetters }, request).then(() => {
        expect(commit).toHaveBeenCalledTimes(3);
        expect(commit).toHaveBeenCalledWith("setUserStatus", {
          status: "loading",
        });
        expect(commit).toHaveBeenCalledWith("setUserStatus", {
          status: "success",
        });
        expect(commit).toHaveBeenCalledWith("setLoggedInUser", expected);
        expect(mock.history.get.length).toEqual(1);
        expect(mock.history.get[0].url).toEqual(`/users/1`);
      });
    });
  });
  describe("edit user", () => {
    it("edit user", async () => {
      const commit = jest.fn();
      const request: EditUserRequest = {
        username: "newUsername",
        password: "newPassword",
        oldPassword: "password",
        email: "newemail@newemail.com",
      };
      const body = {
        id: 1,
        request,
      };
      const rootGetters = {
        getToken: "token",
      };
      const response: any = {
        id: 1,
        username: "newUsername",
        email: "newemail@newemail.com",
      };
      const expected: User = {
        id: 1,
        username: "newUsername",
        email: "newemail@newemail.com",
      };
      const editUser = actions.editUser as Function;
      mock.onPost(`/users/${body.id}/edit`).reply(200, response);

      await editUser({ commit, rootGetters }, body).then(() => {
        expect(commit).toHaveBeenCalledTimes(3);
        expect(commit).toHaveBeenCalledWith("setUserStatus", {
          status: "loading",
        });
        expect(commit).toHaveBeenCalledWith("setUserStatus", {
          status: "success",
        });
        expect(commit).toHaveBeenCalledWith("setLoggedInUser", expected);
        expect(mock.history.post.length).toEqual(1);
        expect(mock.history.post[0].url).toEqual(`/users/1/edit`);
      });
    });
  });
});
