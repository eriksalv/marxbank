import { UserState } from "../../store/modules/users/types";
import { mount } from "@vue/test-utils";
import { createStore, Store } from "vuex";
import { Plugin } from "@vue/runtime-core";
import MyProfile from "../../views/MyProfile.vue";

describe("My profile", () => {
  const initState: UserState = {
    userStatus: "",
    loggedInUser: {
      id: 1,
      username: "username",
      email: "email@email.com",
    },
  };
  let mockGetLoggedInUser: jest.Mock<any, any>;
  let mockGetToken: jest.Mock<any, any>;
  let mockGetUserId: jest.Mock<any, any>;
  let mockLogout: jest.Mock<any, any>;
  let mockFetchUserById: jest.Mock<any, any>;
  let mockEditUser: jest.Mock<any, any>;
  let store: Store<UserState> | Plugin | [Plugin, ...any[]];

  beforeEach(() => {
    mockGetLoggedInUser = jest.fn().mockReturnValue(initState.loggedInUser);
    mockGetToken = jest.fn();
    mockGetUserId = jest.fn().mockReturnValue(initState.loggedInUser.id);
    mockLogout = jest.fn();
    mockFetchUserById = jest.fn();
    mockEditUser = jest.fn();
    store = createStore({
      state: initState,
      getters: {
        getLoggedInUser: mockGetLoggedInUser,
        getToken: mockGetToken,
        getUserId: mockGetUserId,
      },
      actions: {
        logout: mockLogout,
        fetchUserById: mockFetchUserById,
        editUser: mockEditUser,
      },
    });
  });

  test("test initial state", async () => {
    const wrapper = mount(MyProfile, {
      global: { plugins: [store] },
    });

    expect(mockFetchUserById).toBeCalledTimes(1);
    expect(mockGetLoggedInUser).toBeCalledTimes(1);
    expect(mockGetUserId).toHaveBeenCalledTimes(1);
    expect(wrapper.html()).toContain(initState.loggedInUser.username);
    expect(wrapper.vm.$data.newUsername).toBe(null);
    expect(wrapper.vm.$data.newPassword).toBe(null);
    expect(wrapper.vm.$data.currentPassword).toBe(null);
    expect(wrapper.vm.$data.newEmail).toBe(null);
    expect(wrapper.vm.$data.errorMsg).toBe(null);
    expect(wrapper.vm.$data.successMsg).toBe(null);
  });

  test("logout", async () => {
    const mockRouter = {
      push: jest.fn(),
    };

    const wrapper = mount(MyProfile, {
      global: {
        plugins: [store],
        mocks: {
          $router: mockRouter,
        },
      },
    });

    expect(mockLogout).toBeCalledTimes(0);

    await wrapper.find("#logout").trigger("click");

    expect(mockLogout).toHaveBeenCalledTimes(1);
    expect(mockGetToken).toHaveBeenCalledTimes(1);
    expect(mockRouter.push).toHaveBeenCalledTimes(1);
    expect(mockRouter.push).toHaveBeenCalledWith("/login");
  });

  test("edit user", async () => {
    const wrapper = mount(MyProfile, {
      global: { plugins: [store] },
    });
    const usernameInput = wrapper.find("#username");
    const emailInput = wrapper.find("#email");
    const passwordInput = wrapper.find("#password");
    const currentPasswordInput = wrapper.find("#currentPassword");

    await usernameInput.setValue("newUsername");
    await passwordInput.setValue("newPassword");
    await emailInput.setValue("newemail@newemail.com");
    await currentPasswordInput.setValue("password");
    await wrapper.find("#edit").trigger("click");

    expect(mockEditUser).toHaveBeenCalledTimes(1);
    expect(wrapper.vm.$data.errorMsg).toBe(false);
    expect(wrapper.vm.$data.successMsg).toEqual("Changes saved successfully");
  });
});
