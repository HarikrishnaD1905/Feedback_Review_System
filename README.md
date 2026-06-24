## Feedback Management System

A full-stack web application that allows users to submit feedback through a public form and enables an administrator to manage, analyze, and respond to that feedback through a secure admin panel.

The public side provides a simple form where users can submit their name, email, feedback content, and an optional star rating. The system validates all inputs and confirms the submission instantly.

The admin side is protected by JWT-based authentication and includes a full feedback management interface — view all submissions in a paginated table, search and filter by name, email, status, or category, update the status of any feedback, assign categories, and delete entries when needed. Every critical action is recorded in an immutable audit trail.

The admin dashboard provides a quick overview of key metrics including total submissions, average rating, and visual breakdowns by status and category. The system also sends automated email notifications to the admin whenever new feedback arrives.

Built with **Spring Boot** (backend) and **React + Axios** (frontend).
