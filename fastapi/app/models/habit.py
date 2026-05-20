from sqlalchemy import Column, String, DateTime, Boolean, Integer, ForeignKey
from sqlalchemy.dialects.postgresql import UUID
import uuid
from datetime import datetime
from app.database import Base

class Habit(Base):
    __tablename__ = "habits"

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4)
    task_id = Column(UUID(as_uuid=True), ForeignKey("tasks.id"), nullable=False)
    target_days = Column(Integer, nullable=False)
    color_hex = Column(String, nullable=False)
    created_at = Column(DateTime, default=datetime.utcnow)

class HabitLog(Base):
    __tablename__ = "habit_logs"

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4)
    habit_id = Column(UUID(as_uuid=True), ForeignKey("habits.id"), nullable=False)
    log_date = Column(DateTime, nullable=False)
    is_done = Column(Boolean, default=False)
    created_at = Column(DateTime, default=datetime.utcnow)
