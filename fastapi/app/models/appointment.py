from sqlalchemy import Column, String, DateTime, Boolean, Integer, ForeignKey
from sqlalchemy.dialects.postgresql import UUID
import uuid
from datetime import datetime
from app.database import Base

class Appointment(Base):
    __tablename__ = "appointments"

    id = Column(UUID(as_uuid=True), primary_key=True, default=uuid.uuid4)
    task_id = Column(UUID(as_uuid=True), ForeignKey("tasks.id"), nullable=False)
    date_time = Column(DateTime, nullable=False)
    location = Column(String)
    reminder_mins = Column(Integer)
    is_dday = Column(Boolean, default=False)
    created_at = Column(DateTime, default=datetime.utcnow)
