Proceed with a global UX/UI and data-management update across all modules of the application (Tasks, Habits, Objectives, Notes, Finance, Agenda, etc.).

The goal is to replace the current direct deletion system with a safer and more premium archive-based workflow inspired by modern productivity applications.

Global Deletion & Archive System Update:

1. Replace Direct Delete with “Move to Archive”

* Remove the current instant delete behavior from all module data rows/cards/items.
* Any delete action should now first move the selected item into an Archive section instead of deleting it permanently.
* This interaction should feel smooth and intentional.

2. Confirmation Modal Before Archiving

* Before moving an item to archive, display a clean confirmation dialog/modal:

  * Title: “Are you sure?”
  * Subtitle explaining the item will be moved to archive instead of permanently deleted.
* Include:

  * Cancel button
  * Confirm button
* Use premium Material 3 modal styling with rounded corners and soft animations.

3. Archive Section for Every Module

* Each module page must contain its own dedicated Archive section/list.
* Archived data rows should remain separated from active content.
* The archive section can either:

  * exist as a collapsible dropdown section
    OR
  * a dedicated “Archive” subpage/screen.

4. Archived Item Interactions
   Archived data rows must support swipe/slide gestures.

Swipe Right:

* Restore the archived item back into the active list.

Swipe Left:

* Permanently delete the archived item.

These gestures should:

* feel fluid and responsive
* include subtle background indicators/icons during swipe
* use smooth animations
* support hold + swipe interactions naturally.

5. Bulk Archive Clearing
   Inside every archive section:

* Add a “Delete All Archived Items” button.
* This action permanently clears all archived data rows from that module.
* This action must also trigger a confirmation modal before execution.

6. Archived Row UI Styling
   Archived items should visually feel:

* muted
* slightly faded
* secondary to active content

Recommended styling:

* reduced opacity
* softer text color
* subtle archived icon indicator
* clean compact cards

7. UX Behavior Requirements

* Use undo/snackbar feedback after archiving an item.
* Preserve smooth animations for:

  * archive transition
  * restore transition
  * permanent deletion
* Avoid harsh destructive UI behavior.
* The entire archive system should feel safe, reversible, and premium.

8. Consistency Across Modules
   Apply this archive workflow consistently across:

* Tasks
* Habits
* Objectives
* Notes
* Finance transactions
* Agenda events
* Any other editable data row/module in the application.

Overall Design Direction:

* Minimal productivity aesthetic
* Calm Material 3 design language
* Smooth gestures and transitions
* Rounded UI elements
* Elegant destructive-action handling
* Premium mobile UX inspired by modern productivity ecosystems
