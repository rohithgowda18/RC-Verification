import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Shield, ArrowLeft } from "lucide-react";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";

const Analytics = () => {
  const navigate = useNavigate();

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
            <h1 className="text-xl font-bold">Fraud Analytics</h1>
          </div>
        </div>
      </header>

      <main className="container mx-auto px-4 py-8">
        <Card className="shadow-elevated">
          <CardHeader>
            <CardTitle>Fraud Detection Analytics</CardTitle>
            <CardDescription>
              View fraud statistics, trends, and region-wise heatmaps
            </CardDescription>
          </CardHeader>
          <CardContent>
            <p className="text-muted-foreground">
              Analytics dashboard coming soon. This will display verification trends, fraud patterns, and comprehensive reports for law enforcement.
            </p>
          </CardContent>
        </Card>
      </main>
    </div>
  );
};

export default Analytics;