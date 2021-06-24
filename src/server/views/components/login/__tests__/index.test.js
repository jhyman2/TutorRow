import React from 'react'
import { render, fireEvent, waitFor, screen } from '@testing-library/react'
import '@testing-library/jest-dom/extend-expect'
import Login from '../'

test('loads and displays login page', async () => {
    render(<Login />)

  
    await waitFor(() => screen.getByRole('heading'))
  
    expect(screen.getByRole('heading')).toHaveTextContent('Welcome to TutorRow')
  })