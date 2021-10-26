import {render, fireEvent} from '@testing-library/vue'
import Home from '../../views/Home.vue'

test('properly handles v-model', async () => {
  const {getByLabelText, getByText} = render(Home)

  // Asserts initial state.
  getByText('This is a home page')
})