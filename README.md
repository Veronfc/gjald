# 📦 Archived

'Gjald' started as a Spring MVC project for invoice and customer management but it became clear that there was a need for a dynamic and interactive UI — particularly when creating invoices with multiple nested line items and real-time calculations like subtotals, tax, and totals.

Spring MVC, with its server-rendered HTML via Thymeleaf, wasn't well-suited for this level of interactivity without unnecessary complexity or workarounds. These limitations led to a misalignment between technology and requirements.

*A future version of Gjald may be revived using Spring Boot as a REST API backend paired with a Vue + Vite frontend for better separation of concerns and UX.*
<br/><br/>

# 💸 Gjald
A Spring Boot MVC application for creating, managing, and exporting **invoices**. Gjald is designed to be a clean, server-rendered invoicing system.
<br/><br/>

## 🚀 Summary
Gjald allows you to:
- **Create and manage customers** and **catalog items** (products or services).
- **Build invoices** with one or more line items, calculating tax and totals automatically.
- **Track invoice status** — draft, sent, paid, overdue.
- **Export invoices as PDFs** for download.

Ideal for demonstrating **MVC architecture**, relational entity design, and structured validation with Spring Boot.
<br/><br/>

## ✅ Key Features
- **Customer Management**: Add and edit clients with contact info.
- **Product/Service Catalog**: Define reusable items for invoicing.
- **Invoice Generator**: Calculates line item totals, tax, and grand totals.
- **Status Tracking**: Track and update invoices through various states.
- **PDF Export**: Generate printable invoices directly from server-side templates.
- **Form Validation**: Integrated constraint-based validation using Hibernate Validator.
- **Entity Relationships**: Designed using realistic one-to-many and many-to-one mappings.
<br/><br/>

## 🛠️ Tech Stack & Dependencies
- **Spring Boot** — Standalone web app framework.
- **Spring MVC (Thymeleaf)** — Server-rendered HTML with form binding and templates.
- **Spring Data JPA** — ORM and repository abstraction.
- **Hibernate Validator (Jakarta Validation)** — Form and data integrity enforcement.
- **PostgreSQL** — Primary database (via Supabase).
- **Maven** — Build and dependency management.
- *(PDF generation pending integration — likely iText or Flying Saucer.)*

Gjald uses a **package-by-feature** structure and adheres to clear MVC separation. All invoices and totals are calculated server-side.