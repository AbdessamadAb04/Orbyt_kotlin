from pydantic import BaseModel
from datetime import datetime
from enum import Enum
from typing import Optional

class TaskType(str, Enum):
    SIMPLE = "SIMPLE"
    HABIT = "HABIT"
    APPOINTMENT = "APPOINTMENT"

class TaskPriority(str, Enum):
    HIGH = "HIGH"
    NORMAL = "NORMAL"
    LOW = "LOW"

class TaskStatus(str, Enum):
    TODO = "TODO"
    DONE = "DONE"

class TaskBase(BaseModel):
    title: str
    description: Optional[str] = None
    type: TaskType
    priority: TaskPriority
    status: TaskStatus
    due_date: Optional[datetime] = None
    nebula_id: Optional[str] = None

class TaskCreate(TaskBase):
    pass

class TaskUpdate(TaskBase):
    pass

class Task(TaskBase):
    id: str
    created_at: datetime
    updated_at: datetime

    class Config:
        orm_mode = True
