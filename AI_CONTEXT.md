# AI CONTEXT AND RULEBOOK
*Bất cứ khi nào bạn (AI) mở một phiên làm việc mới với chúng tôi, bạn PHẢI đọc và tuân thủ tuyệt đối các quy tắc dưới đây.*

## 1. Project Identity
- **Name:** Mini Design-Pattern Showcase Application.
- **Domain:** Notification Dispatcher System.
- **Goal:** Academic project focused on Software Design Quality, OOP principles, and Design Patterns. Feature quantity does not matter; code quality is everything.

## 2. Core Restrictions for AI Generation
- **DO NOT** generate monolithic "God Classes".
- **MUST** enforce strict Encapsulation (all fields private, expose via properties/getters-setters, include validation in setters).
- **MUST** apply Design Patterns exactly as requested by the user. Do not over-engineer or mix patterns unless explicitly asked.
- The 4 patterns we are using:
  1. Factory Method (Creational)
  2. Decorator (Structural)
  3. Strategy (Behavioral)
  4. Observer (Behavioral)

## 3. OOP Principles to Emphasize
When writing or refactoring code for us, ensure these are clearly visible:
- **Liskov Substitution Principle (LSP):** Subtypes (`Email`, `SMS`) must be perfectly substitutable for the base type (`Notification`).
- **Open/Closed Principle (OCP):** The core dispatcher should not be modified when a new `NotificationType` or `DispatchStrategy` is added.
- **Polymorphism:** Favor dynamic method dispatch (e.g., calling `notification.Send()` where the behavior is resolved at runtime).

## 4. Testing Focus
When asked to write tests, provide:
1. Happy path (everything works).
2. Validation/Error paths (e.g., passing `null` or negative values, catching Exceptions).
3. Polymorphism verification (asserting that swapping a strategy changes the outcome).

## 5. Tone and Explanation
- Always explain *why* a pattern was used in the context of resolving a "Code Smell".
- Keep code clean, well-commented (explaining the "Why", not the "What"), and ready for academic grading.