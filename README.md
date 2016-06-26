# LFC Raleigh

LFC Raleigh is a web site for a local supporters group for Liverpool Football Club. It will provide some standard information about the group and our events.

The main piece of the project will be interaction with several APIs - specifically the Paypal public API to take dues payments from members on the website.

Members will be stored in a PostgreSQL database so administrators can retrieve information about members, their email addresses, and their current status on dues.

## Navigation
The navigation bar will allow users to hop between a few different pages

- Home page, displaying news items
- About Us page for information about the club
- Membership page describing the benefits of membership and allowing users to pay dues
- Upcoming Events page, displaying events the club will be participating in (added by administrators)
- Admin page, allowing administrators to log in and be redirected to an admin panel

#### Home page
The home page will contain news items that can be entered by administrators through the admin panel. These will be stored in a SQL database with their title, content, author and timestamp.

This page will also have a list of recent LFC Raleigh tweets in the right margin.

#### About Us
This will be a static page displaying some information about the club, our history and who visitors can contact for more information.

#### Membership
This is the centerpiece of the project - this page will allow users to pay dues using the Paypal API. They will enter a first name, last name, email address, credit card information, and whether they would like to join our mailing list. It will return a receipt for them to prove that their payment has been received. At a minimum it will also email the club president and myself (club treasurer) to add the user to the mailing list if the user desired. Ideally it will interface with the Mailchimp API to add the user automatically.

#### Upcoming Events
This page will display upcoming events based on the current date and time and a events table populated by administrators through the admin panel.

## Admin
This will lead to a login page for administrators - the six founders/officers of the club. If a login is incorrect, they will be redirected to the login page with a notification that their credentials were not found. If the login is incorrect, they will be redirected to a separate admin landing page.

#### Admin Panel
From the admin panel, administrators will be able to retrieve a list of members or search for specific members. This will provide information on the member's name, email address, and status on dues. They can also add news items for the front page or upcoming events for the events page.

#### Member Listing
This page will retrieve all members in the database, along with their email address and their dues status. Admins will be able to search the list.

#### Manage News
Admins can add, edit, or remove news items to be posted on the main page. These will consist of a title, body, author and timestamp.

#### Manage Events
Admins can add, edit, or remove events to be posted on the upcoming events page. These will consist of a title, a description, a date and time.

#### Twitter
Although not part of the minimum viable product, eventually the admin panel will allow administrators to post a tweet to the official LFC Raleigh account without having to log in or visit the Twitter site.


## Minimum Viable Product
For this project, the minimum viable product is a functioning website that interfaces with PostgreSQL to add news and events, with a membership page that accepts credit cards through Paypal and adds members to the database.
