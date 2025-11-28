import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Shield, ArrowLeft, Trash2, RefreshCw, Eye } from "lucide-react";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { apiClient } from "@/lib/api";
import { Input } from "@/components/ui/input";
import { toast } from "sonner";

const Vehicles = () => {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [items, setItems] = useState<any[]>([]);
  const [adminKey, setAdminKey] = useState("");

  const loadAll = async () => {
    try {
      setLoading(true);
      const data = await apiClient.rc.getAll();
      const safe = (Array.isArray(data) ? data : []).filter((x) => x && typeof x === "object");
      setItems(safe);
    } catch (err: any) {
      toast.error(err.message || "Failed to fetch vehicles");
    } finally {
      setLoading(false);
    }
  };

  const removeItem = async (id: string) => {
    if (!adminKey) {
      toast.error("Enter admin key to delete");
      return;
    }
    try {
      setLoading(true);
      await apiClient.rc.remove(id, adminKey);
      toast.success("Vehicle deleted");
      setItems(prev => prev.filter(v => v.id !== id));
    } catch (err: any) {
      toast.error(err.message || "Delete failed");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => { loadAll(); }, []);

  return (
    <div className="min-h-screen bg-gradient-hero">
      <header className="border-b bg-card/50 backdrop-blur-sm sticky top-0 z-10">
        <div className="container mx-auto px-4 py-4 flex items-center gap-4">
          <Button variant="ghost" size="sm" onClick={() => navigate("/dashboard")}>
            <ArrowLeft className="h-4 w-4 mr-2" />
            Back
          </Button>
          <div className="flex items-center gap-2">
            <Shield className="h-6 w-6 text-primary" />
            <h1 className="text-xl font-bold">Vehicle Database</h1>
          </div>
        </div>
      </header>

      <main className="container mx-auto px-4 py-8">
        <Card className="shadow-elevated">
          <CardHeader>
            <CardTitle>Vehicle Management</CardTitle>
            <CardDescription>
              Manage and view all registered vehicles in the system
            </CardDescription>
          </CardHeader>
          <CardContent>
            <div className="flex items-center gap-2 mb-4">
              <Input
                placeholder="Admin key for delete"
                value={adminKey}
                onChange={(e) => setAdminKey(e.target.value)}
                className="max-w-xs"
              />
              <Button variant="outline" size="sm" onClick={loadAll} disabled={loading}>
                <RefreshCw className="h-4 w-4 mr-2" />
                Refresh
              </Button>
            </div>

            {loading ? (
              <p className="text-muted-foreground">Loading...</p>
            ) : items.length === 0 ? (
              <p className="text-muted-foreground">No vehicles found.</p>
            ) : (
              <div className="grid gap-4">
                {items.map((v) => (
                  <Card key={v.id} className="border">
                    <CardHeader className="py-3">
                      <div className="flex items-center justify-between">
                        <div>
                          <CardTitle className="text-base">{v.rcNumber}</CardTitle>
                          <CardDescription>
                            {v.vehicleInfo?.make} {v.vehicleInfo?.model} • {v.owner?.name}
                          </CardDescription>
                        </div>
                        <div className="flex items-center gap-2">
                          <Button
                            variant="secondary"
                            size="sm"
                            onClick={() => v?.id && navigate(`/rc/${v.id}`)}
                            disabled={!v?.id}
                          >
                            <Eye className="h-4 w-4 mr-1" /> View
                          </Button>
                          <Button
                            variant="destructive"
                            size="sm"
                            onClick={() => v?.id && removeItem(v.id)}
                            disabled={!v?.id}
                          >
                          <Trash2 className="h-4 w-4 mr-1" /> Delete
                          </Button>
                        </div>
                      </div>
                    </CardHeader>
                    <CardContent className="pt-0 text-sm">
                      <div className="grid sm:grid-cols-2 gap-2">
                        <div>
                          <div>QR: {v.qrCodeId}</div>
                          <div>Owners Count: {v.ownersCount}</div>
                          <div>Previous Owners: {Array.isArray(v.previousOwners) ? v.previousOwners.join(", ") : ""}</div>
                        </div>
                        <div>
                          <div>Chassis: {v.vehicleInfo?.chassisNumber}</div>
                          <div>Engine: {v.vehicleInfo?.engineNumber}</div>
                          <div>Insurance: {v.insurance?.provider} ({v.insurance?.policyNumber})</div>
                        </div>
                      </div>
                    </CardContent>
                  </Card>
                ))}
              </div>
            )}
          </CardContent>
        </Card>
      </main>
    </div>
  );
};

export default Vehicles;