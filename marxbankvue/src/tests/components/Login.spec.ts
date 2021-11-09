import { mount } from "@vue/test-utils";
import LoginComponent from "../../components/LoginComponent.vue";
import RegisterComponent from "../../components/RegisterComponent.vue";
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

  test("click login", async () => {
    const wrapper = mount(LoginComponent, {
      global: { plugins: [store] },
    });
    const loginBtn = wrapper.find("button");

    await loginBtn.trigger("click");

    expect(mockLogin).toHaveBeenCalledTimes(1);
  });

  test("switch to register", async () => {
    const wrapper = mount(LoginComponent, {
      global: { plugins: [store] },
    });

    await wrapper.find("#createNewUser").trigger("click");

    expect(wrapper.vm.$data.register).toEqual(true);

    await wrapper.find("#back").trigger("click");

    expect(wrapper.vm.$data.register).toEqual(false);
  });

  test("input", async () => {
    const wrapper = mount(LoginComponent, {
      global: { plugins: [store] },
    });
    const usernameInput = wrapper.find("#username");
    const passwordInput = wrapper.find("#password");

    await usernameInput.setValue("test");
    await passwordInput.setValue("pass");

    expect(wrapper.vm.$data.username).toEqual("test");
    expect(wrapper.vm.$data.password).toEqual("pass");
  });
});

describe("Register", () => {
  const initState = {
    status: "",
    token: null,
    userId: null,
  };
  let mockSignup: jest.Mock<any, any>;
  let mockAuthStatus: jest.Mock<any, any>;
  let store:
    | Store<{ status: string; token: null; userId: null }>
    | Plugin
    | [Plugin, ...any[]];

  beforeEach(() => {
    mockSignup = jest.fn();
    mockAuthStatus = jest.fn();
    store = createStore({
      state: initState,
      getters: {
        authStatus: mockAuthStatus,
      },
      actions: {
        signup: mockSignup,
      },
    });
  });

  test("test initial state", () => {
    const wrapper = mount(RegisterComponent, {
      global: { plugins: [store] },
    });

    expect(wrapper.vm.$data.username).toEqual("");
    expect(wrapper.vm.$data.password).toEqual("");
    expect(wrapper.vm.$data.email).toEqual("");

    expect(wrapper.vm.$data.repeatPassword).toEqual("");
    expect(mockAuthStatus).toHaveBeenCalled();
  });

  test("click signup", async () => {
    const wrapper = mount(RegisterComponent, {
      global: { plugins: [store] },
    });
    const signupBtn = wrapper.find("button");

    await signupBtn.trigger("click");

    expect(mockSignup).toHaveBeenCalledTimes(1);
  });

  test("input", async () => {
    const wrapper = mount(RegisterComponent, {
      global: { plugins: [store] },
    });
    const usernameInput = wrapper.find("#username");
    const emailInput = wrapper.find("#email");
    const passwordInput = wrapper.find("#password");
    const repeatInput = wrapper.find("#repeatPassword");

    await usernameInput.setValue("test");
    await emailInput.setValue("email@email.com");
    await passwordInput.setValue("pass");
    await repeatInput.setValue("pass");

    expect(wrapper.vm.$data.username).toEqual("test");
    expect(wrapper.vm.$data.email).toEqual("email@email.com");
    expect(wrapper.vm.$data.password).toEqual("pass");
    expect(wrapper.vm.$data.repeatPassword).toEqual("pass");
  });

  test("passwords dont match", async () => {
    const wrapper = mount(RegisterComponent, {
      global: { plugins: [store] },
    });
    const usernameInput = wrapper.find("#username");
    const emailInput = wrapper.find("#email");
    const passwordInput = wrapper.find("#password");
    const repeatInput = wrapper.find("#repeatPassword");
    const signupBtn = wrapper.find("button");

    await usernameInput.setValue("test");
    await emailInput.setValue("email@email.com");
    await passwordInput.setValue("pass");
    await repeatInput.setValue("pas");
    await signupBtn.trigger("click");

    expect(mockSignup).toHaveBeenCalledTimes(0);
  });
});
