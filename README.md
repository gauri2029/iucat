# 📚 IU CAT – Library Management System

![CI/CD Pipeline](https://github.com/gauri2029/iucat/actions/workflows/ci-cd.yml/badge.svg)
![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.6-brightgreen)

A Spring Boot web application for managing library operations including book rentals, holds/queue system, and automated notifications.

**Live Demo:** [https://iucat-library.onrender.com/](https://iucat-library.onrender.com/)

---

## 🔑 Test Credentials

| Username | Password | Notes |
|----------|----------|-------|
| `gsmarkan@iu.edu` | `password123` | Has a rental due in 2 days (for notification testing) |
| `abc` | `password123` | Regular user account |
| `pqr` | `password123` | Regular user account |
| `admin` | `adminpassword123` | Admin account |

---

## ✨ Key Features

### 1. **User Authentication & Session Management**
- Secure login with role-based access (USER/ADMIN)
- Session persistence across pages

### 2. **Book Search & Discovery**
- Search by Title, Author, Subject/ISBN, or All Fields
- **Advanced filters**: Author, Subject, Format, Language, Year, Availability
- **AJAX-based filtering (no page reload)**
- 100+ books in catalog

### 3. **Book Rental System**
- Rent books with 14-day due date
- Track rental status (active/returned/overdue)
- Automatic inventory management
- Prevent duplicate rentals

### 4. **Extension Management**
- Extend due date up to **2 times** (14 days each extension)
- Extension restrictions:
  - Cannot extend if overdue
  - Cannot extend if holds exist on the book
  - Maximum 2 extensions enforced
- Real-time extension count display

### 5. **Holds/Queue System**
- Place holds on unavailable books
- Automatic queue management with position tracking
- Hold status: PENDING → READY (7 days to pickup)
- Automatic queue progression when book returned or hold cancelled
- Hold expiration after 7 days

### 6. **Notification System**
- **Scheduled Task:** Runs daily at midnight (Spring @Scheduled)
- Email reminders for books due in 2 days
- Login alert banner for due-soon books
- Email service configured (disabled in demo mode)

---

## 🛠 Technology Stack

**Backend:** Java 17, Spring Boot 3.5.6, Spring Data JPA, Spring Scheduler, Hibernate  
**Frontend:** Thymeleaf, HTML5, CSS3, JavaScript (AJAX)  
**Database:** SQLite (dev), H2 (production on Render)  
**DevOps:** Docker, GitHub Actions CI/CD, Render.com  
**Testing:** JUnit 5, Spring Boot Test

---

## 🚀 Quick Start

### Local Setup
```bash
# Clone repository
git clone https://github.com/gauri2029/iucat.git
cd iucat

# Build and run
./mvnw spring-boot:run

# Access at
http://localhost:8080
```

### Run Tests
```bash
./mvnw test
```

---

## 🧪 Testing Guide

### Test Flow 1: Complete Rental Workflow
1. **Login:** Use `abc` / `password123`
2. **Search:** Search for "Atomic Habits"
3. **Rent:** Click "View Details" → "Rent This Book"
4. **Verify:** Check "My Rentals" - due date is 14 days from today
5. **Extend:** Click "Extend Due Date" (can do this 2 times)
   - First extension: Due date +14 days, shows "1/2 extensions"
   - Second extension: Due date +14 days, shows "2/2 extensions"
   - Third attempt: Should show "Extension limit reached" error
6. **Return:** Click "Return Book"

### Test Flow 2: Holds System
1. **Setup:** Login as `abc`, rent all copies of a book (e.g., "MongoDB: The Definitive Guide" - only 1 copy)
2. **Place Hold:** 
   - Logout, login as `pqr`
   - Search for same book → "Place Hold"
   - Verify: "My Holds" shows PENDING, Queue position #1
3. **Ready Hold:**
   - Logout, login as `abc` → Return the book
   - Logout, login as `pqr` → Check "My Holds"
   - Verify: Status changed to READY, expiration date shown
4. **Pickup:** Click "Pick Up / Rent Now" → Verifies book appears in "My Rentals"

### Test Flow 3: Advanced Search & Filters
1. **Login:** Any user account
2. **Search:** Enter "Java" in search box
3. **Filter:** Click on sidebar filters (Author, Subject, Format, etc.)
4. **Verify:** Results update without page reload (AJAX)
5. **Clear:** Click "✕ Clear All Filters"

### Test Flow 4: Due Date Notification
1. **Login:** Use `gsmarkan@iu.edu` / `password123`
2. **Verify:** Red alert banner appears: "Book Due Soon! You have 1 book(s) due in 2 days"
3. **Check:** Click "View My Rentals" to see the due-soon book

### Test Flow 5: Extension Restrictions
1. **Setup:** Login as `abc`, rent a book
2. **Place Hold:** Login as `pqr`, place hold on same book
3. **Try Extension:** Login as `abc`, try to extend the rental
4. **Verify:** Error message: "Cannot extend, other users have placed holds on this book"

---

## 📊 CI/CD Pipeline

**GitHub Actions workflow includes:**
1. **Build & Test:** Compile code, run all unit tests
2. **Docker Build:** Create and push Docker image to Docker Hub
3. **Deploy:** Automatic deployment to Render.com on push to `main`

**View Status:** [GitHub Actions](https://github.com/gauri2029/iucat/actions)

---

## 🗄 Database Schema

```
users (id, username, password, role)
books (id, isbn, title, author, available_copies, total_copies, publication_year, language, format, subject)
rentals (id, user_id, book_id, rental_date, due_date, return_date, status, extension_count, extension_limit)
holds (id, user_id, book_id, hold_date, expiration_date, status, queue_position)
```

**Key Relationships:**
- User → Rentals (1:Many)
- User → Holds (1:Many)
- Book → Rentals (1:Many)
- Book → Holds (1:Many)

---

## 🔗 Key API Endpoints

### Authentication
- `GET/POST /login` - User login
- `GET /logout` - Logout

### Books
- `GET /search` - Search with filters
- `GET /books/{id}` - Book details

### Rentals
- `GET /my-rentals` - User's rentals
- `POST /rent/{bookId}` - Rent book
- `POST /extend/{rentalId}` - Extend due date
- `POST /return/{rentalId}` - Return book

### Holds
- `GET /my-holds` - User's holds
- `POST /holds/place/{bookId}` - Place hold
- `POST /holds/cancel/{holdId}` - Cancel hold
- `POST /holds/pickup/{holdId}` - Pickup ready hold

---

## ✅ Feature Verification Checklist

**Authentication:**
- ✅ Login with valid credentials
- ✅ Session persistence
- ✅ Logout clears session

**Search:**
- ✅ Basic search (all fields, title, author, subject)
- ✅ Advanced filters (AJAX, no page reload)
- ✅ Filter combinations work

**Rentals:**
- ✅ Rent available books (14-day due date)
- ✅ Extend up to 2 times (14 days each)
- ✅ Extension limit enforced
- ✅ Cannot extend if holds exist
- ✅ Return books
- ✅ Inventory updates correctly

**Holds:**
- ✅ Place holds on unavailable books
- ✅ Queue position tracking
- ✅ PENDING → READY status change
- ✅ 7-day pickup expiration
- ✅ Automatic queue progression
- ✅ Cancel holds
- ✅ Pickup converts to rental

**Notifications:**
- ✅ Alert banner for due-soon books
- ✅ Scheduled task configured (@Scheduled)
- ✅ Email service integrated (logs only in demo)

**Data Integrity:**
- ✅ No duplicate rentals
- ✅ No duplicate holds
- ✅ Accurate inventory counts
- ✅ Queue positions update correctly

---

## 📝 Important Notes

### Scheduled Task (Notifications)
- **Cron:** `0 0 0 * * ?` (runs daily at midnight)
- **Function:** Finds rentals due in 2 days, sends email reminders
- **Status:** Email sending disabled in demo mode (logs notifications)
- **Test Account:** `gsmarkan@iu.edu` has a pre-configured rental due in 2 days

### Extension System
- **Limit:** Maximum 2 extensions per rental
- **Duration:** Each extension adds 14 days
- **Restrictions:** 
  - Blocked if book is overdue
  - Blocked if holds exist on the book
  - Blocked after reaching limit (2/2)

### Hold Expiration
- **Ready Holds:** 7 days to pick up after becoming ready
- **Auto-Expire:** Expired holds automatically release book to next in queue
- **Queue:** First-come-first-served, automatic position updates

---

## 🐛 Known Limitations

1. **Email Notifications:** Disabled on Render (demo mode), logs only
2. **Database:** Resets on Render dyno restart (free tier limitation)
3. **Cold Start:** First request takes 30-60 seconds (Render free tier)
4. **Session Storage:** In-memory (lost on restart)

---

## 📦 Project Structure

```
src/
├── main/
│   ├── java/ed/iu/p566/iucat/
│   │   ├── config/DataLoader.java          # Initial data seeding
│   │   ├── controllers/                    # MVC Controllers
│   │   ├── data/                           # JPA Repositories
│   │   ├── model/                          # Entity classes
│   │   ├── service/                        # Business logic
│   │   └── IucatApplication.java           # Main class
│   └── resources/
│       ├── templates/                      # Thymeleaf views
│       ├── static/css/                     # Stylesheets
│       └── application.properties          # Configuration
└── test/                                   # JUnit tests
```

---

**Repository:** [github.com/gauri2029/iucat](https://github.com/gauri2029/iucat)  
**Live Demo:** [iucat-library.onrender.com](https://iucat-library.onrender.com/)
