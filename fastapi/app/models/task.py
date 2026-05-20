from sqlalchemy import Column, String, DateTime, Boolean, Integer, Float, Enum
from sqlalchemy.dialects.postgresql import UUID
import uuid
from datetime import datetime
from app.database import Base
from app.schemas.task import TaskType, TaskPriority, TaskStatus

class Task(Base):
    __tablename__ = "tasks"

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4)
    title = Column(String, nullable=False)
    description = Column(String)
    type = Column(Enum(TaskType), nullable=False)
    priority = Column(Enum(TaskPriority), nullable=False)
    status = Column(Enum(TaskStatus), nullable=False)
    due_date = Column(DateTime)
    nebula_id = Column(UUID(as_uuid=True))
    created_at = Column(DateTime, default=datetime.utcnow)
    updated_at = Column(DateTime, default=datetime.utcnow, onupdate=datetime.utcnow)
