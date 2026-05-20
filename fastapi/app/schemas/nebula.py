from pydantic import BaseModel
from datetime import datetime
from enum import Enum
from typing import Optional

class NebulaStatus(str, Enum):
    ACTIVE = "ACTIVE"
    COMPLETED = "COMPLETED"
    PAUSED = "PAUSED"
    ABANDONED = "ABANDONED"

class NebulaBase(BaseModel):
    title: str
    description: Optional[str] = None
    color_hex: str
    target_date: Optional[datetime] = None
    status: NebulaStatus
    progress: float = 0.0

class NebulaCreate(NebulaBase):
    pass

class NebulaUpdate(NebulaBase):
    pass

class Nebula(NebulaBase):
    id: str
    created_at: datetime
    updated_at: datetime

    class Config:
        orm_mode = True
