import {
  Dialog,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
} from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";

interface OrderConfirmModalProps {
  isOpen: boolean;
  onConfirm: () => void;
  onCancel: () => void;
  totalAmount: number;
}

export default function OrderConfirmModal({
  isOpen,
  onConfirm,
  onCancel,
  totalAmount,
}: OrderConfirmModalProps) {
  return (
    <Dialog open={isOpen} onOpenChange={(open) => !open && onCancel()}>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>주문 확인</DialogTitle>
          <DialogDescription>
            아래 내용으로 주문하시겠습니까?
          </DialogDescription>
        </DialogHeader>
        <div className="py-4">
          <p className="text-lg font-semibold">
            총 주문금액: {totalAmount.toLocaleString()}원
          </p>
        </div>
        <DialogFooter>
          <Button variant="outline" onClick={onCancel}>
            취소
          </Button>
          <Button onClick={onConfirm}>
            주문하기
          </Button>
        </DialogFooter>
      </DialogContent>
    </Dialog>
  );
} 