from pydantic import BaseModel
from datetime import datetime

class HabitBase(BaseModel):
    task_id: str
    target_days: int
    color_hex: str

class HabitCreate(HabitBase):
    pass

class HabitUpdate(HabitBase):
    pass

class Habit(HabitBase):
    id: str
    created_at: datetime

    class Config:
        orm_mode = True

class HabitLogBase(BaseModel):
    habit_id: str
    log_date: datetime
    is_done: bool

class HabitLogCreate(HabitLogBase):
    pass

class HabitLogUpdate(HabitLogBase):
    pass

class HabitLog(HabitLogBase):
    id: str
    created_at: datetime

    class Config:
        orm_mode = True
