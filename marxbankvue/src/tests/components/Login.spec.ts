import { mount } from "@vue/test-utils";
import LoginComponent from "../../components/LoginComponent.vue";
import { createStore, Store } from "vuex";
import { Plugin } from "@vue/runtime-core";

//TODO: mocke vue router https://next.vue-test-utils.vuejs.org/guide/advanced/vue-router.html

describe("Login", () => {
  const initState = {
    status: "",
    token: null,
    userId: null,
  };
  let mockLogin: jest.Mock<any, any>;
  let mockAuthStatus: jest.Mock<any, any>;
  let store:
    | Store<{ status: string; token: null; userId: null }>
    | Plugin
    | [Plugin, ...any[]];

  beforeEach(() => {
    mockLogin = jest.fn();
    mockAuthStatus = jest.fn();
    store = createStore({
      state: initState,
      getters: {
        authStatus: mockAuthStatus,
      },
      actions: {
        login: mockLogin,
      },
    });
  });

  test("test initial state", () => {
    const wrapper = mount(LoginComponent, {
      global: { plugins: [store] },
    });

    expect(wrapper.html()).toContain("The Revolutionaries' Bank");
    expect(wrapper.vm.$data.username).toEqual("");
    expect(wrapper.vm.$data.password).toEqual("");
    expect(wrapper.vm.$data.register).toEqual(false);
    expect(mockAuthStatus).toHaveBeenCalled();
  });

  test("test click login", async () => {
    const wrapper = mount(LoginComponent, {
      global: { plugins: [store] },
    });
    const loginBtn = wrapper.find("button");

    await loginBtn.trigger("click");

    expect(mockLogin).toHaveBeenCalledTimes(1);
  });
});
