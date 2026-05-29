# Luminous Quest Design System

### 1. Overview & Creative North Star
**Creative North Star: The Playful Cartographer**
Luminous Quest is a design system built for discovery and emotional engagement. It moves away from the clinical "SaaS" aesthetic toward a tactile, editorial experience that feels like an interactive storybook. By using a "Playful Cartographer" lens, we prioritize clear wayfinding through high-contrast typography and intentional depth, while using soft-focus background elements to break the rigid digital grid.

The system is defined by its use of "tactile elevations"—elements that don't just sit on a screen but feel like they can be pressed or moved, using slab-style shadows and thick, intentional borders to create a sense of physical presence.

### 2. Colors
Luminous Quest uses a vibrant, fidelity-driven palette where color isn't just decoration—it's information.
- **Primary Role:** The Electric Blue (#135bec) acts as the "action" color, reserved for current progress, active states, and navigation.
- **Semantic Emotive Tones:** Secondary and tertiary roles are expanded to include specific emotive washes (Amber for joy, Blue for calm/sadness, Red for energy/anger).
- **The "No-Line" Rule:** Sectioning is achieved through background shifts (e.g., `#f6f6f8` for the app canvas vs `#ffffff` for cards). 1px lines are prohibited for layout separation; use 2px or 4px borders only when an element needs to feel "stamped" onto the interface.
- **Surface Hierarchy:** 
  - `surface_container_lowest`: Background canvas.
  - `surface`: Primary card level.
  - `surface_container_high`: Floating navigation and headers (using `backdrop-blur-md` at 80% opacity).
- **Glass & Gradient:** Floating elements must use `bg-white/80` with a 12px backdrop blur to maintain contact with the colorful world beneath them.

### 3. Typography
The system uses **Lexend** exclusively. Its geometric clarity and wide character footprint provide a friendly yet authoritative tone.
- **Display (3rem):** Used exclusively for large emoji icons or hero numbers.
- **Headline (1.25rem / 1.125rem):** Bold, tracking-tight. These are the anchors of each section.
- **Title (0.875rem):** Standard body weight for descriptions.
- **Label/Caps (10px):** All-caps, with `tracking-widest` (0.1em). This is used for "meta" information like level numbers or section headers (e.g., "MAP DISCOVERY").
- **Typographic Rhythm:** The scale jumps significantly from small labels (10px) to headers (20px), creating a "Punchy Editorial" look that avoids the monotony of middle-weight font sizes.

### 4. Elevation & Depth
Depth in Luminous Quest is "Tactile," not "Ambient."
- **The Stamped Effect:** Instead of blurry, naturalistic shadows, use the **Slab Shadow**: `0 8px 0 0 rgba(0, 0, 0, 0.05)`. This gives components a 3D-molded plastic feel.
- **Interaction Feedback:** On `:active`, shadows should shrink to `0 2px 0 0`, and the element should `translateY(4px)` to simulate a physical button press.
- **The Layering Principle:** Use a 4px primary border (e.g., Level 3 Active card) to indicate "Focus" rather than increasing shadow depth.
- **Ambient Glows:** Use large, blurred background "blobs" (e.g., `blur-3xl` at 40% opacity) in the corners of the viewport to create a sense of environmental light without adding structural clutter.

### 5. Components
- **Buttons:** All buttons must have a `rounded-full` (9999px) shape. Primary buttons use the seed color with an `animate-pulse` for "Play" states.
- **Emotion Cards:** Rectangular with `rounded-xl` (1.5rem) corners. They must feature a subtle 2px border matching their internal semantic color (e.g., Amber-200 for Happy).
- **Progress Bars:** High-contrast containers (`bg-slate-100`) with rounded-full inner fills. Heights are fixed at 12px (h-3).
- **Avatar Toggles:** Circular with a 4px border "ring" that acts as a progress meter around the user's face.
- **Bottom Navigation:** Solid white background with a crisp top border (`border-t border-slate-200`). Icons use a 24px fill/weight variation to denote selection.

### 6. Do's and Don'ts
- **Do:** Use high-contrast label text (10px, uppercase) to label sections.
- **Do:** Use semantic background washes (Amber, Red, Purple) to categorize content types.
- **Don't:** Use thin, grey 1px borders for cards. Use shadows or tonal shifts instead.
- **Don't:** Use sharp 90-degree corners. Everything in Luminous Quest should feel safe and soft (min 0.5rem radius).
- **Do:** Use transparency (`/80`) on sticky headers to prevent the UI from feeling "boxed in."