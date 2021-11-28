import { UserState } from "../../store/modules/users/types";
import { mount } from "@vue/test-utils";
import { createStore, Store } from "vuex";
import { Plugin } from "@vue/runtime-core";
import MyProfile from "../../views/MyProfile.vue";
import Calculator from "../../views/Calculator.vue";
import TextInput from "../../components/TextInput.vue";

describe("My profile", () => {
  test("initial state", async () => {
    const wrapper = mount(Calculator, {});

    expect(wrapper.vm.$data.showTotalAmount).toEqual(false);
    expect(wrapper.vm.$data.input1).toEqual("");
    expect(wrapper.vm.$data.input2).toEqual("");
    expect(wrapper.vm.$data.input3).toEqual("");
    expect(wrapper.vm.$data.input4).toEqual("");
    expect(wrapper.vm.$data.amount).toEqual(null);
  });

  test("valid calculation", async () => {
    const wrapper = mount(Calculator, {});

    const button = wrapper.find("button");

    wrapper.find("#i1").find("input").setValue("100");
    wrapper.find("#i2").find("input").setValue("1000");
    wrapper.find("#i3").find("input").setValue("20");
    wrapper.find("#i4").find("input").setValue("5");
    await button.trigger("click");

    expect(wrapper.vm.$data.showTotalAmount).toEqual(true);
    expect(wrapper.vm.$data.amount).toEqual(
      "kr  " + Number(43407).toLocaleString()
    );
  });

  test("invalid calculation", async () => {
    const wrapper = mount(Calculator, {});

    const button = wrapper.find("button");

    wrapper.find("#i1").find("input").setValue("10000");
    wrapper.find("#i2").find("input").setValue("1000");
    wrapper.find("#i3").find("input").setValue("2000");
    wrapper.find("#i4").find("input").setValue("500");
    await button.trigger("click");

    expect(wrapper.vm.$data.showTotalAmount).toEqual(true);
    expect(wrapper.vm.$data.amount).toEqual(" beløpet er utenfor rekkevidde!");
  });

  test("period equal to 0 calculation", async () => {
    const wrapper = mount(Calculator, {});

    const button = wrapper.find("button");

    wrapper.find("#i1").find("input").setValue("100");
    wrapper.find("#i2").find("input").setValue("1000");
    wrapper.find("#i3").find("input").setValue("0");
    wrapper.find("#i4").find("input").setValue("10");
    await button.trigger("click");

    expect(wrapper.vm.$data.showTotalAmount).toEqual(true);
    expect(wrapper.vm.$data.amount).toEqual(
      "kr  " + Number(1000).toLocaleString() + "  (engangsbeløp)"
    );
  });

  test("text input", async () => {
    const wrapper = mount(TextInput, {});
  });
});
