# User Guide

* [About](#about)
* [Quick Start](#quick-start)
    * [Installation Instructions](#installation-instructions)
    * [Launch](#launch)
    * [Visual Introduction](#visual-introduction)
* [Features](#features)
* [Command Summary](#command-summary)
* [Frequently Asked Questions](#frequently-asked-questions)
<br>

## About
ezDo is the eziest™ way to get stuff done. Whether you’re planning a holiday, powering through your day or managing multiple work projects, ezDo is here to help you tick off all your personal and professional to-dos.

For an ez™ life, use ezDo™.

<br>

## Quick Start

### Installation Instructions

1. Ensure that you have [Java version `1.8.0_60`](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) or later installed in your computer.<br>
2. Download the latest `ezDo.jar` from the [releases](../../../releases) tab. <p align="center"><img src="images/Release.png" width="500"></p>
3. Copy the latest `ezDo.jar` to the folder you want to use. This folder will be the home folder for ezDo.

<br>

### Launch
To start the application, double-click on `ezDo.jar`. The user interface should appear in a few seconds. <p align="center"><img src="images/Ui.png" width="500"></p><br>

### Visual Introduction
<p align="center"><img src="images/VisualIntroduction.png" width="500"></p>

#### 1. Command Box
Your commands should be typed here.<br>
#### 2. Description Box
The results of your command executions will be shown here.<br>
#### 3. Task Card
Each task in ezDo is represented by a taskcard with the following properties:<br>
* Each taskcard has an index on the left.<br>
* The color on the left indicates its priority. <br>
    * <font color="green">Green</font>: low priority (1)
    * <font color="orange">Orange</font>: medium priority (2)
    * <font color="red">Red</font>: high priority (3)</br>
* Taskcards may have a start/end time.<br>
* Taskcards may have any number of tags associated with it.

Now that we are familiar with ezDo's interface, let us get started with the features of ezDo!

<br>

## Features

**Things to note:**
* Words in `UPPER_CASE` are the parameters.
* Parameters in `SQUARE_BRACKETS` are optional.
* Parameters with `...` after them can have multiple instances (separated by a white space).

<br>

|Command|Description                                                |
|:-----:|:---------------------------------------------------------:|
|[add](#1-adding-a-task-add--a)             |Add a task             |
|[edit](#2-editing-a-task-edit--e)          |Edit a task            |
|[done](#3-marking-a-task-as-done-done--d)  |Mark a task as done    |
|[kill](#4-deleting-a-task-kill--k)         |Delete a task          |
|[sort](#5-sorting-a-list-of-tasks-sort--s) |Sort the list of tasks |
|[find](#6-finding-tasks-find--f)           |Search for a task      |
|[list](#7-listing-all-tasks-list--l)       |List the tasks         |
|[undo](#8-undoing-the-last-action-undo--u) |Revert the last action |
|[clear](#9-clearing-all-entries-clear--c)  |Delete all tasks       |
|[save](#10-moving-the-save-file-save)      |Save EzDo to a path    |
|[quit](#11-exiting-the-program-quit--q)    |Quit EzDo              |
|[help](#12-viewing-help-help--h)           |Show the user guide    |

<br>

### 1. Adding a task: `add / a`
---
Adds a task to ezDo.<br>
Format:
**`add TASKNAME [p/PRIORITY] [s/STARTDATE] [d/DUEDATE] [t/TAGNAME1] [t/TAGNAME2]...`**
<br>
> - The task name must be inputted as the first parameter; all entries following the task name are optional.<br>
> - You can tag a task with any number of tags.<br>
> - The format for STARTDATE and DUEDATE, if required for a task, must be DD/MM/YYYY.<br>
> - Priority can be of any value between 1 and 3, with 1 being the lowest priority and 3 being the highest priority.

<br>

Examples:
* You need to buy milk:
**`add Buy milk`** <br>
* You need to buy plane tickets to Hong Kong urgently:
**`add Buy plane tickets to Hong Kong p/3`** <br>
* You need to buy a table by 03/02/2017:
**`add Buy a table d/03/02/2017 t/watchingTV`** <br>
* You need to start marking CS2101 reflections on 01/03/2017:
**`add Mark CS2101 reflections s/01/03/2017  t/school`** <br>

<br>

### 2. Editing a task: `edit / e`
---
Edits a particular task by <u>specifying its index</u> and <u>its new information to be updated</u>.<br>
Format:
**`edit INDEX [NEWTASKNAME] [p/NEWPRIORITY] [s/NEWSTARTDATE] [d/NEWDUEDATE] [t/NEWTAGNAME]...`**
<br>
> - If a task does not have any information in the specified field previously, the new information will be added to the field instead. <br>
> - To clear a field (except task name which cannot be cleared), type out the prefix but leave it blank.

<br>

Example:

You have just added this task to ezDo at index 1 with the Add command:

 **`add Buy milk p/1 t/NTUC`**

You can now edit the task in several ways:

* You wish to change the task name:
**`edit 1 Buy milk and cereal`** <br>
* You wish to change the task's priority to 3:
**`edit 1 p/3`** <br>
* You wish to add a due date:
**`edit 1 d/05/07/2017`**

<br>

### 3. Marking a task as done: `done / d`
---
Marks the task at a specified index as done.<br>
Format:
**`done INDEX`**
<br>
> - The index must be a positive integer.
> - The index refers to the index number of the tasks shown in the most recent listing.
> - Once a task at the specified index is marked as done, it will be removed from the task list and added to the done list.
> - You can view the done list with the command `done` without any index specified.

<br>

Example:
* Marks the task at index 3 as done:
**`done 3`** <br>

<br>

### 4. Deleting a task: `kill / k`
---
Marks the task at a specified index as deleted.<br>
Format:
**`kill INDEX`**
<br>
> - The index must be a positive integer.
> - The index refers to the index number of the tasks shown in the most recent listing.
> - Once a task at the specified index is deleted, it will be removed from the task list.

<br>

Example:
* Deletes the task at index 3:
**`kill 3`** <br>

<br>

### 5. Sorting a list of tasks: `sort / s`
---

Sorts the list of tasks according to the specified ordertype lexicographically.<br>
Format:
**`sort ORDERTYPE`**
<br>
> `ORDERTYPE` can be any of the following:
> - n - name
> - p - priority
> - s - start date
> - d - due date

<br>

Example:

You have just added a few tasks to ezDo with the Add command:

 **`add Dye hair p/1 s/06/05/2017 d/07/08/2017`**<br>
 **`add Buy milk p/1 s/04/05/2017 d/09/06/2017`**<br>
 **`add Call milkman p/1 s/05/05/2017 d/08/07/2017`**

You can now sort the tasks by name:

<p align="center"><img src="images/Sorting.png" width="700"></p>


<br>

### 6. Finding tasks: `find / f`
---
Finds tasks whose information contains any of the given keywords specified by its prefix. <br>
Format:
**`find [KEYWORD] [MORE_KEYWORDS] [p/PRIORITY] [s/STARTDATE] [d/DUEDATE] [t/TAGNAME1] [t/TAGNAME2]...`**
<br>
> - At least one field is needed to search.
> - The search is case insensitive.
> e.g. `find Milk` will match the task with the name `milk`. <br>
> - The order of the keywords does not matter.
> e.g. `find Buy Milk` will match the task with the name `Milk Buy`. <br>
> - Only full words will be matched.
> e.g. `find Milk` will not match the task with the name `Milks`. <br>
> - Only tasks matching all keywords will be returned.
    e.g. `find Milk p/1` will not match with the task `Buy Milk` with priority of 2.
    e.g. `find donuts milk` will match the task with the name `Buy donuts and milk`.<br>
> - After finding your task, type `list` to go back to the default view.

<br>

Examples:
*  You can find tasks that have a due date on `20/03/2017`:
**`find d/20/03/2017`** <br>
*  You can find tasks containing `Buy`, `The`, and `Milk`:
**`find Buy The Milk`** <br>
*  You can find tasks containing `School` with a priority of `1`:
**`find School p/1`** <br>

<br>

### 7. Listing all tasks: `list / l`
---
Lists all the tasks in ezDo. <br>
Format:
**`list`**

<br>

### 8. Reverts the command: `undo / u`
---
Reverts the last command. <br>
Format:
**`undo`**
<br>
> - You can only undo the following commands - `add/edit/kill/clear/done`.

<br>

Example:
*  You just deleted the task: `buy milk` but you can restore it to the task list with the command - `undo`


<br>

### 9. Clearing all entries: `clear / c`
---
Clears all entries from ezDo. <br>
Format:
**`clear`**
<br>

### 10. Moving the save file: `save`
---
Moves the save file of ezDo to a specified directory. <br>
Format:
**`save DIRECTORY`**
<br>
> - The directory specified must be valid.
> - Admin permissions are required.

<br>

Example:
* Moves the save file of ezDo to `C:/Desktop`:
**`save C:/Desktop`** <br>

<br>

### 11. Exiting the program: `quit / q`
---
Exits the program. <br>
Format:
**`quit`**
<br>
> - There will be no exit confirmation!

<br>

### 12. Viewing help: `help / h`
---
Brings up the help guide in a separate window. <br>
Format:
**`help`**


<br>

## Frequently Asked Questions

**Question: How do I transfer my data to another computer?** <br>
**Answer**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous ezDo.
<br>

**Question: How do I save my ezDo tasks?** <br>
**Answer**: ezDo data is saved in the hard disk automatically after any command that changes the data. There is no need to save manually.
<br>

## Command Summary

|Command|Description                                                |
|:-----:|---------------------------------------------------------|
|[add](#1-adding-a-task-add--a)             |**`add TASKNAME [p/PRIORITY] [s/STARTDATE] [d/DUEDATE] [t/TAGNAME]...`**             |
|[edit](#2-editing-a-task-edit--e)          |**`edit INDEX [NEWTASKNAME] [p/NEWPRIORITY] [s/NEWSTARTDATE] [d/NEWDUEDATE] [t/NEWTAGNAME...]`**            |
|[done](#3-marking-a-task-as-done-done--d)  |**`done INDEX`**    |
|[kill](#4-deleting-a-task-kill--k)         |**`kill INDEX`**          |
|[sort](#5-sorting-a-list-of-tasks-sort--s) |**`sort ORDERTYPE`** |
|[find](#6-finding-tasks-find--f)           |**`find [KEYWORD] [MORE_KEYWORDS] [p/PRIORITY] [s/STARTDATE] [d/DUEDATE] [t/TAGNAME]`**      |
|[list](#7-listing-all-tasks-list--l)       |**`list`**         |
|[undo](#8-undoing-the-last-action-undo--u) |**`undo`** |
|[clear](#9-clearing-all-entries-clear--c)  |**`clear`**       |
|[save](#10-moving-the-save-file-save)      |**`save DIRECTORY`**    |
|[quit](#11-exiting-the-program-quit--q)    |**`quit`**              |
|[help](#12-viewing-help-help--h)           |**`help`**    |
