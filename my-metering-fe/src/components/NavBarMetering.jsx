import React from 'react'
import { Navbar, Nav, Container } from 'react-bootstrap'
import { Link } from "react-router-dom";

const NavBarMetering = () => {
  return (
    <div>
      <header>
        <Navbar justify="true" expand="xxl" data-bs-theme='dark' style={{backgroundColor: '#174708'}}>
          <Container>
            <Navbar.Brand as={Link} to="/dashboard">Dashboard</Navbar.Brand>
            <Navbar.Toggle/>
            <Navbar.Collapse>
            <Nav fill="true" variant='tabs' className='me-auto' style={{ width: '100%' }}>
              <Nav.Item>
                <Nav.Link href="/logout" title='Logout'>Logout</Nav.Link>
              </Nav.Item>
            </Nav>
            </Navbar.Collapse>
          </Container>
        </Navbar>
      </header>
    </div>
  )
}

export default NavBarMetering