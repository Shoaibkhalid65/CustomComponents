# ⏱️ Speed Meter

A **custom Speed Meter gauge** built entirely using **Jetpack Compose Canvas**.
The project demonstrates how to draw and animate a **speedometer UI** with arcs, ticks, labels, and a smoothly animated needle using the **low-level `Animatable` API**.

---

## 📸 Demo



https://github.com/user-attachments/assets/c14123f9-58d3-4904-ae28-e1d3b3c2e47b



---

## ✨ Features

* **Canvas Drawing** – Uses `DrawScope` functions like `drawArc`, `drawLine`, and `drawCircle` to render the speedometer.
* **Smooth Animations** – Needle movement is powered by the low-level **`Animatable` API**, ensuring precise frame-by-frame control.
* **Dynamic Input** – The speed value can be updated at runtime, and the needle animates towards it.
* **Responsive Layout** – Works across multiple device sizes using Compose’s modifiers.
* **Customizable** – Colors, max speed, arc thickness, and animation duration can be easily modified.

---

## 🛠️ Tech Stack

* **Language**: Kotlin
* **Framework**: Jetpack Compose
* **Graphics**: Canvas + DrawScope APIs (`drawArc`, `drawLine`, etc.)
* **Animation**: `Animatable` (low-level API for fine-grained animation control)

---


## 📚 Learning Outcomes

By building this project, you will learn:

* How to use **Canvas in Jetpack Compose** for custom UI drawing.
* Practical use of **DrawScope functions** like `drawArc`, `drawLine`, and `drawCircle`.
* How to map real values (speed) into **angles** with trigonometry.
* Implementing **low-level animations** using `Animatable`.
* Designing a fully **custom component** without relying on external libraries.

---

👉 If you find this project useful, please ⭐ star the repo to support!

---

