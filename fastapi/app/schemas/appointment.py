from pydantic import BaseModel
from datetime import datetime
from typing import Optional

class AppointmentBase(BaseModel):
    task_id: str
    date_time: datetime
    location: Optional[str] = None
    reminder_mins: Optional[int] = None
    is_dday: bool

class AppointmentCreate(AppointmentBase):
    pass

class AppointmentUpdate(AppointmentBase):
    pass

class Appointment(AppointmentBase):
    id: str
    created_at: datetime

    class Config:
        orm_mode = True
