import { AuthState } from "@/store/modules/auth/types";
import { getters } from "../store/modules/auth/getters";
import { mutations } from '../store/modules/auth/mutations'
import { actions } from '../store/modules/auth/actions'
import { LoginRequest, LoginResponse } from "@/types/types";
import axios from "axios";
import AxiosMockAdapter from 'axios-mock-adapter'

const rootState = {
  message: "hello",
};

const testState: AuthState = {
  status: "",
  token: null,
  userId: null
};

describe("getters", () => {
    it("test isLoggedIn", () => {
        const actual = getters.isLoggedIn(testState, null, rootState, null)

        expect(actual).toEqual(false)
    }),
    it("test get token", () => {
        const actual = getters.getToken(testState, null, rootState, null)

        expect(actual).toEqual(null)
    }),
    it("test status", () => {
        const actual = getters.authStatus(testState, null, rootState, null)

        expect(actual).toEqual("")
    })
})

describe("mutations", () => {

    it("test setToken", () => {
      const newToken = "t"
  
      mutations.setToken(testState, newToken)

      const isLoggedIn = getters.isLoggedIn(testState, null, rootState, null)
  
      expect(testState.token).toEqual(newToken)
      expect(isLoggedIn).toEqual(true)
    })
    it("test setStatus", () => {
        const newStatus = "error"

        mutations.setStatus(testState, newStatus)

        expect(testState.status).toEqual("error")
    }),
    it("test setUserId", () => {
        const newId = 2

        mutations.setUserId(testState, newId)

        expect(testState.userId).toEqual(newId)
    })
})

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
            const commit = jest.fn()
            const username = "user"
            const password = "password"
            const request: LoginRequest = {
                username,
                password
            }  
            const response: any = {
                userResponse: {
                    id: 1,
                },
                token: "token"
            }
            const login = actions.login as Function
            mock.onPost("/auth/login").reply(200, response)
    
            await login({ commit, testState }, request).then(() => {
                expect(commit).toHaveBeenCalledTimes(4)
                expect(commit).toHaveBeenCalledWith("setStatus", "loading");
                expect(commit).toHaveBeenCalledWith("setStatus", "success");
                expect(commit).toHaveBeenCalledWith("setUserId", 1);
                expect(commit).toHaveBeenCalledWith("setToken", "token");
                expect(mock.history.post.length).toEqual(1);
                expect(mock.history.post[0].url).toEqual(`/auth/login`);
                expect(mock.history.post[0].data).toBe(JSON.stringify(request));
            })
        }),
        it("login error", async () => {
            const commit = jest.fn()
            const badRequest = {}
            const login = actions.login as Function;
            mock.onPost(`/auth/login`).networkErrorOnce();

            await login({ commit, testState }, badRequest).then(() => {
                expect(commit).toHaveBeenCalledTimes(2);
                expect(commit).toHaveBeenCalledWith("setStatus", "loading");
                expect(commit).toHaveBeenCalledWith("setStatus", "error");
                expect(mock.history.post.length).toEqual(1);
                expect(mock.history.post[0].url).toEqual(`/auth/login`);
                expect(mock.history.post[0].data).toBe(JSON.stringify(badRequest));
            })
        })
    })
})