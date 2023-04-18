import React, { useEffect, useState } from 'react'
import { redirect, useNavigate, useParams } from 'react-router-dom'
import { loadDriversPackages } from '../../store/driversPackages'
import { useAppDispatch } from '../../store/hooks'
import { loadHubWorkersPackages } from '../../store/hubworkersPackages'
import { setLoginUser } from '../../store/loginUser'
import { loadClientMessages } from '../../store/messages'
import { loadClientPackages } from '../../store/packages'
import useInput from '../UseInput/UseInput'

const LoginPage = () => {
  const navigate = useNavigate();

  const [inputUsername, username] = useInput("", "Username")
  const [inputPassword, password] = useInput("", "Password")

  const dispatch = useAppDispatch()

  const submit = () => {
    fetch('/login', {
        method: 'POST',
        headers: {'Content-Type':'application/json'},
        body: JSON.stringify({
         "username": username,
         "password": password 
        })
       }).then((response) => {
        const authHeader = response.headers.get('authorization')
        let token = ""
        if (authHeader) {
           token = authHeader?.substring(7, authHeader?.length);
        }

        localStorage.setItem('token', token);
        localStorage.setItem('username', username);

        console.log(localStorage.getItem('token'));

        fetch(`api/user/${username}/role`).then(response => response.json()).then((role) => {
          localStorage.setItem('role', role.name);
          if (role.name === 'ROLE_CLIENT') {
            navigate('/clientPackages')
          } else if (role.name === 'ROLE_HUBWORKER') {
            navigate('/hubsPackagesList')
          } else if (role.name === 'ROLE_DRIVER') {
            navigate('/driverPackages')
          }
        })
       })
  }

  const changePage = () => {
    console.log(localStorage.getItem('role'))
    if (localStorage.getItem('role') === 'ROLE_CLIENT') {
      navigate('/clientPackages')
    } else if (localStorage.getItem('role') === 'ROLE_HUBWORKER') {
      navigate('/hubsPackagesList')
    } else if (localStorage.getItem('role') === 'ROLE_DRIVER') {
      navigate('/driverPackages')
    }
  }

  const messagesButton = () => {
    console.log(localStorage.getItem('role'))
    if (localStorage.getItem('role') === 'ROLE_CLIENT') {
      navigate('/clientMessages')
    }
  }
  
  return <div>
      {localStorage.getItem('token') === null  &&
      <div> 
        <p>Login to see</p>
        {inputUsername}
        <br></br>
        {inputPassword}
        <br></br>
        <button onClick={submit}>
          Sign in
        </button>
        </div>
      }
      {localStorage.getItem('token') !== null  &&
      <div> 
        <h1>SHIPMENT APP</h1>
        <h1>WELCOME {localStorage.getItem('username')}</h1>
        <button onClick={changePage}>LET'S SEE YOUR PACKAGES!</button>
        {(localStorage.getItem('role') === 'ROLE_CLIENT') && <button onClick={messagesButton}>LET'S SEE YOUR MESSAGES!</button>}
      </div>
      }
  </div>
}

export default LoginPage