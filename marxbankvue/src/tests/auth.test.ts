import { AuthState } from "@/store/modules/auth/types";
import { getters } from "../store/modules/auth/getters";
import { mutations } from '../store/modules/auth/mutations'

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
      const newToken = "token"
  
      mutations.setToken(testState, newToken)

      const isLoggedIn = getters.isLoggedIn(testState, null, rootState, null)
  
      expect(testState.token).toEqual(newToken)
      expect(isLoggedIn).toEqual(true)
    })
    it("test setStatus", () => {
        const newStatus = "success"

        mutations.setStatus(testState, newStatus)

        expect(testState.status).toEqual("success")
    }),
    it("test setUserId", () => {
        const newId = 1

        mutations.setUserId(testState, newId)

        expect(testState.userId).toEqual(newId)
    })
})

describe("actions", () => {
    it("test login", () => {
        
    })
})