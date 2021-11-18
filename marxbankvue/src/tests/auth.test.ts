import { AuthState } from "@/store/modules/auth/types";
import { getters } from "../store/modules/auth/getters";
import { mutations } from "../store/modules/auth/mutations";
import { actions } from "../store/modules/auth/actions";
import { LoginRequest, LoginResponse, SignUpRequest } from "@/types/types";
import axios from "axios";
import AxiosMockAdapter from "axios-mock-adapter";

const rootState = {
  message: "hello",
};

const testState: AuthState = {
  statusCode: 200,
  status: "",
  token: null,
  userId: null,
};

describe("getters", () => {
  it("test isLoggedIn", () => {
    const actual = getters.isLoggedIn(testState, null, rootState, null);

    expect(actual).toEqual(false);
  }),
    it("test get token", () => {
      const actual = getters.getToken(testState, null, rootState, null);

      expect(actual).toEqual(null);
    }),
    it("test status", () => {
      const actual = getters.authStatus(testState, null, rootState, null);

      expect(actual).toEqual("");
    });
});

describe("mutations", () => {
  it("test setToken", () => {
    const newToken = "t";

    mutations.setToken(testState, newToken);

    const isLoggedIn = getters.isLoggedIn(testState, null, rootState, null);

    expect(testState.token).toEqual(newToken);
    expect(isLoggedIn).toEqual(true);
  });
  it("test setStatus", () => {
    const newStatus = "error";

    mutations.setStatus(testState, newStatus);

    expect(testState.status).toEqual("error");
  }),
    it("test setUserId", () => {
      const newId = 2;

      mutations.setUserId(testState, newId);

      expect(testState.userId).toEqual(newId);
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

  describe("login", () => {
    it("valid login", async () => {
      const commit = jest.fn();
      const username = "user";
      const password = "password";
      const request: LoginRequest = {
        username,
        password,
      };
      const response: any = {
        userResponse: {
          id: 1,
        },
        token: "token",
        status: 200,
      };
      const login = actions.login as Function;
      mock.onPost("/auth/login").reply(200, response);

      await login({ commit, testState }, request).then(() => {
        expect(commit).toHaveBeenCalledTimes(5);
        expect(commit).toHaveBeenCalledWith("setStatus", "loading");
        expect(commit).toHaveBeenCalledWith("setStatus", "success");
        expect(commit).toHaveBeenCalledWith("setUserId", 1);
        expect(commit).toHaveBeenCalledWith("setToken", "token");
        expect(commit).toHaveBeenCalledWith("setStatusCode", 200);
        expect(mock.history.post.length).toEqual(1);
        expect(mock.history.post[0].url).toEqual(`/auth/login`);
        expect(mock.history.post[0].data).toBe(JSON.stringify(request));
      });
    });
    it("login error", async () => {
      const commit = jest.fn();
      const badRequest = {};
      const login = actions.login as Function;
      mock.onPost(`/auth/login`).reply(404);

      try {
        await login({ commit, testState }, badRequest);
      } catch (e) {
        expect(commit).toHaveBeenCalledTimes(3);
        expect(commit).toHaveBeenCalledWith("setStatus", "loading");
        expect(commit).toHaveBeenCalledWith("setStatus", "error");
        expect(commit).toHaveBeenCalledWith("setStatusCode", 404);
        expect(mock.history.post.length).toEqual(1);
        expect(mock.history.post[0].url).toEqual(`/auth/login`);
        expect(mock.history.post[0].data).toBe(JSON.stringify(badRequest));
      }
    });
  });

  describe("signup", () => {
    it("valid signup", async () => {
      const commit = jest.fn();
      const request: SignUpRequest = {
        username: "user",
        password: "pass",
        email: "email@email.com",
      };
      const response: any = {
        userResponse: {
          id: 1,
        },
        token: "token",
        status: 200,
      };
      const signup = actions.signup as Function;
      mock.onPost("/auth/signup").reply(200, response);

      await signup({ commit, testState }, request).then(() => {
        expect(commit).toHaveBeenCalledTimes(5);
        expect(commit).toHaveBeenCalledWith("setStatus", "loading");
        expect(commit).toHaveBeenCalledWith("setStatus", "success");
        expect(commit).toHaveBeenCalledWith("setUserId", 1);
        expect(commit).toHaveBeenCalledWith("setToken", "token");
        expect(commit).toHaveBeenCalledWith("setStatusCode", 200);
        expect(mock.history.post.length).toEqual(1);
        expect(mock.history.post[0].url).toEqual(`/auth/signup`);
        expect(mock.history.post[0].data).toBe(JSON.stringify(request));
      });
    }),
      it("signup error", async () => {
        const commit = jest.fn();
        const badRequest = {};
        const signup = actions.signup as Function;
        mock.onPost(`/auth/signup`).reply(500);

        try {
          await signup({ commit, testState }, badRequest);
        } catch (e) {
          expect(commit).toHaveBeenCalledTimes(3);
          expect(commit).toHaveBeenCalledWith("setStatus", "loading");
          expect(commit).toHaveBeenCalledWith("setStatus", "error");
          expect(commit).toHaveBeenCalledWith("setStatusCode", 500);
          expect(mock.history.post.length).toEqual(1);
          expect(mock.history.post[0].url).toEqual(`/auth/signup`);
          expect(mock.history.post[0].data).toBe(JSON.stringify(badRequest));
        }
      });
  });

  describe("logout", () => {
    it("valid logout", async () => {
      const commit = jest.fn();
      const request = "token";
      const logout = actions.logout as Function;
      mock.onPost("/auth/logout").reply(200);

      await logout({ commit, testState }, request).then(() => {
        expect(commit).toHaveBeenCalledTimes(2);
        expect(commit).toHaveBeenCalledWith("setStatus", "");
        expect(commit).toHaveBeenCalledWith("setToken", null);
        expect(mock.history.post.length).toEqual(1);
        expect(mock.history.post[0].url).toEqual(`/auth/logout`);
        expect(mock.history.post[0].data).toBe(request);
      });
    });
  });
});
